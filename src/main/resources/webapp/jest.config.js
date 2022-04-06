module.exports = {
  testURL: 'http://192.168.1.102:8090',
  testEnvironment: './tests/PuppeteerEnvironment',
  verbose: false,
  extraSetupFiles: ['./tests/setupTests.js'],
  globals: {
    ANT_DESIGN_PRO_ONLY_DO_NOT_USE_IN_YOUR_PRODUCTION: false,
    localStorage: null,
  },
};
