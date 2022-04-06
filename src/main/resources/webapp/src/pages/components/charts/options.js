import echarts from 'echarts';

const {format} = echarts;

function getLevelOption() {
    return [
        {
            itemStyle: {
                borderColor: '#777',
                borderWidth: 0,
                gapWidth: 1
            },
            upperLabel: {
                show: false
            }
        },
        {
            itemStyle: {
                borderColor: '#555',
                borderWidth: 5,
                gapWidth: 1
            },
            emphasis: {
                itemStyle: {
                    borderColor: '#ddd'
                }
            }
        },
        {
            colorSaturation: [0.35, 0.5],
            itemStyle: {
                borderWidth: 5,
                gapWidth: 1,
                borderColorSaturation: 0.6
            }
        }
    ];
}

export const treeMapOptions = params => ({
    // title: {
    //     text: 'Disk Usage',
    //     left: 'center'
    // },

    tooltip: {
        formatter: function (info) {
            var value = info.value;
            var treePathInfo = info.treePathInfo;
            var treePath = [];

            for (var i = 1; i < treePathInfo.length; i++) {
                treePath.push(treePathInfo[i].name);
            }

            return [
                '<div class="tooltip-title">' + format.encodeHTML(treePath.join('/')) + '</div>',
                params.title + ': ' + format.addCommas(value) + ' %',
            ].join('');
        }
    },

    series: [
        {
            name: params.title,
            type: 'treemap',
            visibleMin: 300,
            label: {
                show: true,
                formatter: '{b}'
            },
            upperLabel: {
                show: true,
                height: 30
            },
            itemStyle: {
                borderColor: '#fff'
            },
            levels: getLevelOption(),
            data: params.data
        }
    ]
})