import { Alert, Space, message, Tabs } from 'antd';
import React, { useState } from 'react';
import ProForm, { ProFormCaptcha, ProFormCheckbox, ProFormText } from '@ant-design/pro-form';
import { useIntl, Link, history, FormattedMessage, SelectLang, useModel } from 'umi';
import Footer from '@/components/Footer';
import { fakeAccountLogin, getFakeCaptcha, LoginParamsType } from '@/services/login';

import styles from './index.less';

const LoginMessage: React.FC<{
  content: string;
}> = ({ content }) => (
  <Alert
    style={{
      marginBottom: 24,
    }}
    message={content}
    type="error"
    showIcon
  />
);

/**
 * 此方法会跳转到 redirect 参数所在的位置
 */
const goto = () => {
  if (!history) return;
  setTimeout(() => {
    const { query } = history.location;
    const { redirect } = query as { redirect: string };
    history.push(redirect || '/');
  }, 10);
};

const Login: React.FC<{}> = () => {
  const [submitting, setSubmitting] = useState(false);
  const [userLoginState, setUserLoginState] = useState<API.LoginStateType>({});
  const [type, setType] = useState<string>('account');
  const { initialState, setInitialState } = useModel('@@initialState');

  const intl = useIntl();
  //查询用户信息
  const fetchUserInfo = async () => {
    //不存在，则调用查询方法
    const userInfo = await initialState?.fetchUserInfo?.();
    //TODO:待完善用户信息赋值
    if (userInfo) {
      setInitialState({
        ...initialState,
        currentUser: userInfo,
      });
    }
  };

  const handleSubmit = async (values: LoginParamsType) => {
    setSubmitting(true);
    try {
      // 登录
      const msg = await fakeAccountLogin({ ...values, type });
      if (msg.code === 200) {
        message.success('登录成功！');
        await fetchUserInfo();
        goto();
        return;
      }
      // 如果失败去设置用户错误信息
      setUserLoginState({status:"error",type:type});
    } catch (error) {
      message.error('登录失败，请重试！');
    }
    setSubmitting(false);
  };
  const { status, type: loginType } = userLoginState;

  return (
    <div className={styles.container}>
      <video src="/loginBG.mp4" style={{width:'100%',height:'100%',objectFit:'cover',position:'absolute',top:0,left:0}} autoPlay loop muted/>
      <div className={styles.content}>
        <div className={styles.top}>
          <div className={styles.header}>
            <div>
              <img src="/login_title_cn.png" alt="中文标题"/>
            </div>
          </div>
        </div>

        <div className={styles.main}>
          <ProForm
            initialValues={{
              autoLogin: true,
            }}
            submitter={{
              searchConfig: {
                submitText: ' ',
              },
              render: (_, dom) => dom.pop(),
              submitButtonProps: {
                loading: submitting,
                size: 'large',
                style: {
                  width: '83%',
                  backgroundColor:'transparent',
                  border:'0',
                  boxShadow:'none',
                  height:'46px',
                },
              },
            }}
            onFinish={async (values) => {
              handleSubmit(values);
            }}
          >
            {status === 'error' && loginType === 'account' && (
              <LoginMessage
                content={intl.formatMessage({
                  id: 'pages.login.accountLogin.errorMessage',
                  defaultMessage: '账户或密码错误（admin/ant.design)',
                })}
              />
            )}
            {type === 'account' && (
              <>
                <div className={styles.usernameInput}>
                  <ProFormText
                    name="dlm"
                    /*fieldProps={{
                      size: 'large',
                      prefix: <UserOutlined className={styles.prefixIcon} />,
                    }}*/
                    placeholder={'请输入用户名!'}
                    rules={[
                      {
                        required: true,
                        message: (
                          <FormattedMessage
                            id="pages.login.username.required"
                            defaultMessage="请输入用户名!"
                          />
                        ),
                      },
                    ]}
                  />
                </div>
                <div className={styles.pwdInput}>
                  <ProFormText.Password
                    name="dlmm"
                    /*fieldProps={{
                      size: 'large',
                      prefix: <LockTwoTone className={styles.prefixIcon} />,
                    }}*/
                    placeholder={'请输入密码!'}
                    rules={[
                      {
                        required: true,
                        message: (
                          <FormattedMessage
                            id="pages.login.password.required"
                            defaultMessage="请输入密码！"
                          />
                        ),
                      },
                    ]}
                  />
                </div>
              </>
            )}
            <div
              style={{
                marginBottom: 28,
              }}
            >
            </div>
          </ProForm>
        </div>
      </div>
      <div className={styles.foot}>
        <Footer />
      </div>

    </div>
  );
};

export default Login;
