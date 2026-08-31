<template>
    <div>
        <div style="display: flex; grid-gap: 10px;">
            <div class="card" style="padding: 20px; flex: 1; height: 400px;" id="bar"></div>
            <div class="card" style="padding: 20px; flex: 1; height: 400px;" id="line"></div>
        </div>
    </div>
</template>

<script setup>
import { reactive, onMounted } from 'vue';
import request from '@/utils/request.js';
import * as echarts from 'echarts';

const barOption = {
    title: {
        text: '各部门员工数量'
    },
    tooltip: {},
    legend: {
        data: ['人数']
    },
    xAxis: {
        data: []
    },
    yAxis: {},
    series: [
        {
            name: '人数',
            type: 'bar',
            data: [],
            itemStyle: {
                normal: {
                    color: function (params) {
                        let colors = ["#c23531", "#2f4554", "#61a0a8", "#d48265", "#91c7ae", "#749f83", "#ca8622", "#bda29a", "#6e7074", "#546570", "#c4ccd3"];
                        return colors[params.dataIndex % colors.length];
                    }
                }
            }
        }
    ]
};

const lineOption = {
    title: {
        text: '近七天发布文章的数量'
    },
    tooltip: {},
    legend: {
        trigger: 'item',
    },
    xAxis: {
        data: []
    },
    yAxis: {},
    series: [
        {
            name: '发布数量',
            type: 'line',
            data: [],
            smooth: true
        }
    ]
};

const data = reactive({
})

//表示在组件挂载完成后执行
onMounted(() => {
    //在dom准备好后再去设置初始化echarts示例
    const barChart = echarts.init(document.getElementById('bar'));

    request.get("/barData").then(res => {
        barOption.xAxis.data = res.data.department//横轴数据
        barOption.series[0].data = res.data.count//纵轴数据
        barChart.setOption(barOption);
    })

    const lineChart = echarts.init(document.getElementById('line'));
    request.get("/lineData").then(res => {        
        lineOption.xAxis.data = res.data.date//横轴数据
        lineOption.series[0].data = res.data.count//纵轴数据
        lineChart.setOption(lineOption);
    })

});


</script>