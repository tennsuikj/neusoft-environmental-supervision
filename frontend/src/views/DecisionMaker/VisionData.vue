<template>
  <div class="screen-container">
    <!-- 顶部标题栏 -->
    <div class="screen-header">
      <div class="header-title">东软空气质量环保公众监督平台</div>
      <div class="header-time">
        <el-icon>
          <Clock />
        </el-icon>
        {{ currentTime }}
      </div>
    </div>

    <!-- 主体三栏布局 -->
    <div class="screen-main">
      <!-- 左侧面板 -->
      <div class="panel-left">
        <!-- 覆盖率仪表盘组 -->
        <div class="gauge-group">
          <div class="gauge-item">
            <div class="chart-title" style="text-align: center;">全国省会城市网格覆盖率(%)</div>
            <div ref="provinceGaugeRef" class="gauge-box"></div>
          </div>
          <div class="gauge-item">
            <div class="chart-title" style="text-align: center;">全国大城市网格覆盖率(%)</div>
            <div ref="cityGaugeRef" class="gauge-box"></div>
          </div>
        </div>

        <!-- AQI等级分布饼图 -->
        <div class="chart-card">
          <div class="chart-title">空气质量指数级别分布</div>
          <div ref="aqiPieRef" class="chart-box"></div>
        </div>

        <!-- 12个月超标趋势折线图 -->
        <div class="chart-card">
          <div class="chart-title">12个月内空气质量超标趋势</div>
          <div ref="trendLineRef" class="chart-box"></div>
        </div>
      </div>

      <!-- 中间地图面板 -->
      <div class="panel-center">
        <div ref="mapChartRef" class="map-box"></div>
        <!-- 底部实时统计 -->
        <div class="stat-footer">
          <div class="stat-item">
            <div class="stat-icon">
              <el-icon :size="34" color="#3ba1ff">
                <UserFilled />
              </el-icon>
            </div>
            <div class="stat-content">
              <div class="stat-num">{{ data.aqiCount }}</div>
              <div class="stat-label">空气质量检测总数量</div>
            </div>
          </div>
          <div class="stat-item">
            <div class="stat-icon">
              <el-icon :size="34" color="#00c48f">
                <CircleCheckFilled />
              </el-icon>
            </div>
            <div class="stat-content">
              <div class="stat-num">{{ data.aqiGoodCount }}</div>
              <div class="stat-label">空气质量良好数量</div>
            </div>
          </div>
          <div class="stat-item">
            <div class="stat-icon">
              <el-icon :size="34" color="#ff7a45">
                <WarningFilled />
              </el-icon>
            </div>
            <div class="stat-content">
              <div class="stat-num">{{ data.aqiBadCount }}</div>
              <div class="stat-label">空气质量污染数量</div>
            </div>
          </div>
        </div>
      </div>

      <!-- 右侧面板 -->
      <div class="panel-right">
        <!-- PM2.5柱状图 -->
        <div class="chart-card">
          <div class="chart-title">悬浮颗粒物（PM2.5）浓度超标累计</div>
          <div ref="pm25BarRef" class="chart-box"></div>
        </div>

        <!-- SO2柱状图 -->
        <div class="chart-card">
          <div class="chart-title">二氧化硫（SO2）浓度超标累计</div>
          <div ref="so2BarRef" class="chart-box"></div>
        </div>

        <!-- CO柱状图 -->
        <div class="chart-card">
          <div class="chart-title">一氧化碳（CO）浓度超标累计</div>
          <div ref="coBarRef" class="chart-box"></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, onUnmounted, nextTick, watch } from 'vue'
import * as echarts from 'echarts'
import { Clock, UserFilled, CircleCheckFilled, WarningFilled } from '@element-plus/icons-vue'
import request from '@/utils/request.js'
import '@/assets/view.css'
import chinaGeoJson from '@/assets/map/china.json'

// ========== 基础配置 ==========
const currentTime = ref('')
let timeTimer = null

// 图表DOM引用
const provinceGaugeRef = ref(null)
const cityGaugeRef = ref(null)
const aqiPieRef = ref(null)
const trendLineRef = ref(null)
const mapChartRef = ref(null)
const pm25BarRef = ref(null)
const so2BarRef = ref(null)
const coBarRef = ref(null)

// 图表实例（全局单例，避免重复初始化）
let provinceGauge = null
let cityGauge = null
let aqiPie = null
let trendLine = null
let mapChart = null
let pm25Bar = null
let so2Bar = null
let coBar = null

// AQI国标配色

// 省份名称映射
const provinceNameMap = {
  // 23个省
  '黑龙江省': '黑龙江省',
  '吉林省': '吉林省',
  '辽宁省': '辽宁省',
  '河北省': '河北省',
  '山西省': '山西省',
  '青海省': '青海省',
  '山东省': '山东省',
  '河南省': '河南省',
  '江苏省': '江苏省',
  '安徽省': '安徽省',
  '浙江省': '浙江省',
  '福建省': '福建省',
  '湖北省': '湖北省',
  '湖南省': '湖南省',
  '广东省': '广东省',
  '海南省': '海南省',
  '四川省': '四川省',
  '贵州省': '贵州省',
  '云南省': '云南省',
  '陕西省': '陕西省',
  '甘肃省': '甘肃省',
  '江西省': '江西省',
  '台湾省': '台湾省',

  // 5个自治区
  '内蒙古省': '内蒙古自治区',
  '广西省': '广西壮族自治区',
  '西藏省': '西藏自治区',
  '宁夏省': '宁夏回族自治区',
  '新疆省': '新疆维吾尔自治区',

  // 4个直辖市
  '北京省': '北京市',
  '天津省': '天津市',
  '上海省': '上海市',
  '重庆省': '重庆市',

  // 2个特别行政区
  '香港省': '香港特别行政区',
  '澳门省': '澳门特别行政区'
}

const data = reactive({
  aqiCount: 0,
  aqiGoodCount: 0,
  aqiBadCount: 0,
  provinceCoverage: 0,
  cityCoverage: 0,
  aqiDistribution: [],
  aqiTrend: [],
  provinceAndPollution: [],
})

const parsePercent = (val) => {
  const num = Number(String(val).replace('%', ''))
  return isNaN(num) ? 0 : num
}

// ========== 实时时间 ==========
const initTime = () => {
  const update = () => {
    const now = new Date()
    currentTime.value = now.toLocaleString('zh-CN', {
      year: 'numeric', month: '2-digit', day: '2-digit',
      hour: '2-digit', minute: '2-digit', second: '2-digit'
    }).replace(/\//g, '-')
  }
  update()
  timeTimer = setInterval(update, 1000)
}

// ========== 1. 半圆仪表盘 ==========
const renderGauge = (dom, chartInstance, value, color) => {
  if (!chartInstance && dom) {
    chartInstance = echarts.init(dom)
  }
  if (!chartInstance) return chartInstance
  console.log('renderGauge', value)
  const numValue = parsePercent(value)
  console.log('numValue', numValue)
  chartInstance.setOption({
    series: [{
      type: 'gauge',
      startAngle: 225,
      endAngle: -45,
      min: 0,
      max: 100,
      radius: '90%',
      splitNumber: 10,
      axisLine: {
        lineStyle: {
          width: 10,
          color: [
            [numValue / 100, color],
            [1, '#2d4568']
          ]
        }
      },
      pointer: {
        icon: 'arrow',
        length: '60%',
        width: 12,
        itemStyle: { color: color }
      },
      axisTick: { show: false },
      splitLine: { show: false },
      axisLabel: {
        color: '#fff',
        fontSize: 10,
        distance: -4
      },
      detail: {
        formatter: '{value}',
        color: color,
        fontSize: 14,
        fontWeight: 'bold',
        offsetCenter: [0, '75%']
      },
      data: [{ value: numValue }]
    }]
  })
  return chartInstance
}

// ========== 2. AQI分布饼图 ==========
const renderPieChart = () => {
  if (!aqiPieRef.value) return
  if (!aqiPie) {
    aqiPie = echarts.init(aqiPieRef.value)
  }

  const pieData = data.aqiDistribution.map((item, idx) => ({
    name: item.chineseExplain,
    value: item.totalCount,
    itemStyle: { color: item.color || '#fff' }
  }))

  aqiPie.setOption({
    tooltip: { trigger: 'item' },
    legend: {
      orient: 'vertical',
      left: 'left',
      top: 'center',
      textStyle: { color: '#d0e0ff', fontSize: 12 },
      itemWidth: 14,
      itemHeight: 10
    },
    series: [{
      name: 'AQI分布',
      type: 'pie',
      radius: ['72%'],
      center: ['65%', '50%'],
      label: { show: false },
      labelLine: { show: false },
      data: pieData
    }]
  })
}

// ========== 3. 趋势折线图 ==========
const renderTrendChart = () => {
  if (!trendLineRef.value) return
  if (!trendLine) {
    trendLine = echarts.init(trendLineRef.value)
  }

  const xData = data.aqiTrend.map(item => item.statMonth)
  const yData = data.aqiTrend.map(item => item.aqiOverCount)

  trendLine.setOption({
    tooltip: { trigger: 'axis' },
    grid: { left: '3%', right: '4%', bottom: '3%', top: '12%', containLabel: true },
    xAxis: {
      type: 'category',
      boundaryGap: false,
      data: xData,
      axisLine: { lineStyle: { color: '#2d4568' } },
      axisLabel: { color: '#d0e0ff', fontSize: 11 }
    },
    yAxis: {
      type: 'value',
      axisLine: { lineStyle: { color: '#2d4568' } },
      axisLabel: { color: '#d0e0ff' },
      splitLine: { lineStyle: { color: '#1e3355' } },
      interval: 1
    },
    series: [{
      name: '超标数量',
      type: 'line',
      symbol: 'circle',
      symbolSize: 6,
      lineStyle: { color: '#3ba1ff', width: 2 },
      itemStyle: { color: '#3ba1ff' },
      areaStyle: {
        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
          { offset: 0, color: 'rgba(59, 161, 255, 0.35)' },
          { offset: 1, color: 'rgba(59, 161, 255, 0.05)' }
        ])
      },
      data: yData
    }]
  })
}

// ========== 4. 中国地图 ==========
const renderMapChart = () => {
  if (!mapChartRef.value) return
  if (!mapChart) {
    mapChart = echarts.init(mapChartRef.value)
  }

  // 处理省份名称，匹配地图文件中的全称
const mapData = data.provinceAndPollution.map(item => {
  let name = item.provinceName
  if (provinceNameMap[name]) {
    name = provinceNameMap[name]
  }
  return {
    name: name,
    value: item.aqiOverCount || 0
  }
})

mapChart.setOption({
  tooltip: {
    trigger: 'item',
    // 自定义格式化，处理NaN空值
    formatter: (params) => {
      const areaName = params.name
      const num = Number.isNaN(params.value) ? 0 : params.value
      return `${areaName}<br/>超标数量：${num}`
    }
  },
  visualMap: {
    type: 'piecewise',
    left: '5%',
    bottom: '12%',
    itemWidth: 16,
    itemHeight: 12,
    itemGap: 10,
    textStyle: { color: '#d0e0ff', fontSize: 12 },
    // 无数据省份统一底色
    outOfRange: {
      color: '#1e3355'
    },
    pieces: [
      { min: 10, label: '10 - ', color: data.aqiAll[5].color },
      { min: 8, max: 9, label: '8 - 9', color: data.aqiAll[4].color },
      { min: 6, max: 7, label: '6 - 7', color: data.aqiAll[3].color },
      { min: 4, max: 5, label: '4 - 5', color: data.aqiAll[2].color },
      { min: 2, max: 3, label: '2 - 3', color: data.aqiAll[1].color },
      { min: 0, max: 1, label: '0 - 1', color: data.aqiAll[0].color }
    ]
  },
  series: [{
    name: '空气质量',
    type: 'map',
    map: 'china',
    roam: false,
    label: {
      show: true,
      color: '#000',
      fontSize: 10
    },
    emphasis: {
      label: { color: '#fff' },
      itemStyle: {
        colorAlpha: 0.7
      }
    },
    itemStyle: {
      borderColor: '#666',
      borderWidth: 1
    },
    data: mapData
  }]
})
}

// ========== 5. 通用柱状图 ==========
const renderBarChart = (dom, chartInstance, dataKey, color) => {
  if (!dom) return chartInstance
  if (!chartInstance) {
    chartInstance = echarts.init(dom)
  }

  const xData = data.provinceAndPollution.map(item => {
    return item.provinceName?.replace('省', '')
  })
  const yData = data.provinceAndPollution.map(item => item[dataKey] || 0)

  chartInstance.setOption({
    tooltip: { trigger: 'axis', axisPointer: { type: 'shadow' } },
    grid: { left: '3%', right: '3%', bottom: '3%', top: '12%', containLabel: true },
    xAxis: {
      type: 'category',
      data: xData,
      axisLine: { lineStyle: { color: '#2d4568' } },
      axisLabel: { color: '#d0e0ff', fontSize: 11,interval: 0 },
      
      
    },
    yAxis: {
      type: 'value',
      axisLine: { lineStyle: { color: '#2d4568' } },
      axisLabel: { color: '#d0e0ff' },
      splitLine: { lineStyle: { color: '#1e3355' } },
      interval: 1
    },
    series: [{
      type: 'bar',
      barWidth: '42%',
      itemStyle: { color, borderRadius: [4, 4, 0, 0] },
      data: yData
    }]
  })
  return chartInstance
}

// ========== 窗口自适应 ==========
const handleResize = () => {
  provinceGauge?.resize()
  cityGauge?.resize()
  aqiPie?.resize()
  trendLine?.resize()
  mapChart?.resize()
  pm25Bar?.resize()
  so2Bar?.resize()
  coBar?.resize()
}

// ========== 加载数据并渲染 ==========
const load = () => {
  Promise.all([
    request.get('/statistics/getAqiCount'),
    request.get('/statistics/getAqiGoodCount'),
    request.get('/statistics/getProvinceCoverage'),
    request.get('/statistics/getCityCoverage'),
    request.get('/statistics/listAqiDistributeTotalStatis'),
    request.get('/statistics/listAqiTrendTotalStatis'),
    request.get('/statistics/listProvinceItemTotalStatis'),
    request.get('/aqi/listAqiAll')
  ]).then(resList => {
    data.aqiCount = resList[0].data
    data.aqiGoodCount = resList[1].data
    data.aqiBadCount = data.aqiCount - data.aqiGoodCount
    data.provinceCoverage = resList[2].data
    data.cityCoverage = resList[3].data
    data.aqiDistribution = resList[4].data
    data.aqiTrend = resList[5].data
    data.provinceAndPollution = resList[6].data
    data.aqiAll = resList[7].data.reverse()

    nextTick(() => {
      // 渲染仪表盘
      provinceGauge = renderGauge(provinceGaugeRef.value, provinceGauge, data.provinceCoverage, '#00e400')
      cityGauge = renderGauge(cityGaugeRef.value, cityGauge, data.cityCoverage, '#ff6b6b')
      // 渲染其他图表
      renderPieChart()
      renderTrendChart()
      renderMapChart()
      // 渲染三个柱状图
      pm25Bar = renderBarChart(pm25BarRef.value, pm25Bar, 'spmOverCount', '#3ba1ff')
      so2Bar = renderBarChart(so2BarRef.value, so2Bar, 'so2OverCount', '#4cd964')
      coBar = renderBarChart(coBarRef.value, coBar, 'coOverCount', '#ff7878')
    })
  })
}

// ========== 生命周期 ==========
onMounted(() => {
  initTime()
  // 核心修复：注册中国地图（必须在渲染地图前执行，且仅执行一次）
  echarts.registerMap('china', chinaGeoJson)

  load()
  window.addEventListener('resize', handleResize)
})

onUnmounted(() => {
  clearInterval(timeTimer)
  window.removeEventListener('resize', handleResize)
    // 销毁所有图表实例
    ;[provinceGauge, cityGauge, aqiPie, trendLine, mapChart, pm25Bar, so2Bar, coBar].forEach(chart => {
      chart?.dispose()
    })
})

// 数据变化时仅更新配置，不重新初始化实例
watch(() => data.aqiDistribution, () => renderPieChart(), { deep: true })
watch(() => data.aqiTrend, () => renderTrendChart(), { deep: true })
watch(() => data.provinceAndPollution, () => {
  renderMapChart()
  pm25Bar = renderBarChart(pm25BarRef.value, pm25Bar, 'spmCount', '#3ba1ff')
  so2Bar = renderBarChart(so2BarRef.value, so2Bar, 'so2Count', '#4cd964')
  coBar = renderBarChart(coBarRef.value, coBar, 'coCount', '#ff7878')
}, { deep: true })
</script>

<style scoped>
.screen-container {
  width: 100vw;
  height: 100vh;
  background: #0a1930;
  overflow: hidden;
  color: #fff;
  display: flex;
  flex-direction: column;
}

/* 顶部标题 */
.screen-header {
  height: 60px;
  background: linear-gradient(90deg, rgba(15, 40, 80, 0) 0%, #1a3a6e 50%, rgba(15, 40, 80, 0) 100%);
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 30px;
  border-bottom: 1px solid #23487a;
}

.header-title {
  font-size: 22px;
  font-weight: bold;
  letter-spacing: 2px;
  background: linear-gradient(90deg, #4facfe 0%, #00f2fe 100%);
  -webkit-background-clip: text;
  background-clip: text;
  -webkit-text-fill-color: transparent;
}

.header-time {
  font-size: 16px;
  color: #a0c4ff;
  display: flex;
  align-items: center;
  gap: 6px;
}

/* 主体布局 */
.screen-main {
  flex: 1;
  display: flex;
  padding: 15px;
  gap: 15px;
  overflow: hidden;
}

.panel-left,
.panel-right {
  width: 28%;
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.panel-center {
  flex: 1;
  display: flex;
  flex-direction: column;
}

/* 仪表盘组 */
.gauge-group {
  display: flex;
  gap: 15px;
}

.gauge-item {
  flex: 1;
  background: rgba(22, 45, 82, 0.4);
  border: 1px solid #23487a;
  border-radius: 4px;
  padding: 10px 12px;
}

.gauge-box {
  width: 100%;
  height: 140px;
}

/* 通用卡片 */
.chart-card {
  background: rgba(22, 45, 82, 0.4);
  border: 1px solid #23487a;
  border-radius: 4px;
  padding: 10px 15px;
  flex: 1;
  display: flex;
  flex-direction: column;
}

.chart-title {
  font-size: 14px;
  color: #cfe2ff;
  margin-bottom: 8px;
  font-weight: 500;
}

.chart-box {
  flex: 1;
  width: 100%;
  min-height: 120px;
}

/* 地图 */
.map-box {
  flex: 1;
  width: 100%;
  min-height: 400px;
}

/* 底部统计栏 */
.stat-footer {
  display: flex;
  justify-content: space-around;
  padding: 15px 0;
  border-top: 1px solid #23487a;
  margin-top: 10px;
}

.stat-item {
  display: flex;
  align-items: center;
  gap: 12px;
}

.stat-num {
  font-size: 28px;
  font-weight: bold;
  color: #fff;
  line-height: 1;
}

.stat-label {
  font-size: 13px;
  color: #a0c4ff;
  margin-top: 4px;
}
</style>