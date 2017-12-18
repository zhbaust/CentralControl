<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- ECharts单文件引入 -->
<script type="text/javascript" src="echarts/echarts.js"></script>
<title>Echarts Demo</title>
</head>
<body>

	<style type="text/css">
#Container {
	width: 96%;
	margin: 0 auto; /*设置整个容器在浏览器中水平居中*/
	/* background: #CF3; */
}

#Header {
	height: 150px;
	background: #093;
}

#logo {
	padding-left: 50px;
	padding-top: 20px;
	padding-bottom: 50px;
}

#Content {
	height: 100%;
	/*此处对容器设置了高度，一般不建议对容器设置高度，一般使用overflow:auto;属性设置容器根据内容自适应高度，如果不指定高度或不设置自适应高度，容器将默认为1个字符高度，容器下方的布局元素（footer）设置margin-top:属性将无效*/
	margin-top: 20px; /*此处讲解margin的用法，设置content与上面header元素之间的距离*/
	background: #0FF;
}

#Content-Left {
	height: 400px;
	width: 20%;
	margin: 10px; /*设置元素跟其他元素的距离为20像素*/
	float: left; /*设置浮动，实现多列效果，div+Css布局中很重要的*/
	background: #90C;
}

#Content-Main {
	height: 400px;
	width: 40%;
	margin: 10px; /*设置元素跟其他元素的距离为20像素*/
	float: left; /*设置浮动，实现多列效果，div+Css布局中很重要的*/
	background: #90C;
}

#Content-Right {
	height: 400px;
	width: 33%;
	margin: 10px; /*设置元素跟其他元素的距离为20像素*/
	float: left; /*设置浮动，实现多列效果，div+Css布局中很重要的*/
	background: #90C;
}
#Content-Leftdown {
	height: 200px;
	width: 20%;
	margin: 10px; /*设置元素跟其他元素的距离为20像素*/
	float: left; /*设置浮动，实现多列效果，div+Css布局中很重要的*/
	background: #90C;
}

#Content-Maindown {
	height: 200px;
	width: 32%;
	margin: 10px; /*设置元素跟其他元素的距离为20像素*/
	float: left; /*设置浮动，实现多列效果，div+Css布局中很重要的*/
	background: #90C;
}
#Content-Main1down {
	height: 200px;
	width: 20%;
	margin: 10px; /*设置元素跟其他元素的距离为20像素*/
	float: left; /*设置浮动，实现多列效果，div+Css布局中很重要的*/
	background: #90C;
}
#Content-Rightdown {
	height: 200px;
	width: 20%;
	margin: 10px; /*设置元素跟其他元素的距离为20像素*/
	float: left; /*设置浮动，实现多列效果，div+Css布局中很重要的*/
	background: #90C;
}
/*注：Content-Left和Content-Main元素是Content元素的子元素，两个元素使用了float:left;设置成两列，这个两个元素的宽度和这个两个元素设置的padding、margin的和一定不能大于父层Content元素的宽度，否则设置列将失败*/
#Footer {
	height: 40px;
	background: #90C;
	margin-top: 20px;
}

.Clear {
	clear: both;
}
</style>
	</head>

<body>
<div id="Container">
    <div id="Header">
       
    </div>
    <div id="Content">
        <div id="Content-Left">Content-Left</div>
        <div id="Content-Main">Content-Main</div>
        <div id="Content-Right">Content-Right</div>
    </div>
    <div id="Content">
        <div id="Content-Leftdown">Content-Left</div>
        <div id="Content-Maindown">Content-Main</div>
        <div id="Content-Main1down">Content-Main1</div>
        <div id="Content-Rightdown">Content-Right</div>
    </div>
    <div class="Clear">
			<!--如何你上面用到float,下面布局开始前最好清除一下。-->
		</div>
    <!-- <div id="Footer">Footer</div> -->
</div>


	<script type="text/javascript">
	var myChart = echarts.init(document.getElementById('Header'));

	option = {
		    title : {
		        text: '2013年上半年上证指数'
		    },
		    tooltip : {
		        trigger: 'axis',
		        formatter: function (params) {
		            var res = params[0].seriesName + ' ' + params[0].name;
		            res += '<br/>  开盘 : ' + params[0].value[0] + '  最高 : ' + params[0].value[3];
		            res += '<br/>  收盘 : ' + params[0].value[1] + '  最低 : ' + params[0].value[2];
		            return res;
		        }
		    },
		    legend: {
		        data:['上证指数']
		    },
		    toolbox: {
		        show : true,
		        feature : {
		            mark : {show: true},
		            dataZoom : {show: true},
		            dataView : {show: true, readOnly: false},
		            magicType: {show: true, type: ['line', 'bar']},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    dataZoom : {
		        show : true,
		        realtime: true,
		        start : 50,
		        end : 100
		    },
		    xAxis : [
		        {
		            type : 'category',
		            boundaryGap : true,
		            axisTick: {onGap:false},
		            splitLine: {show:false},
		            data : [
		                "2013/1/24", "2013/1/25", "2013/1/28", "2013/1/29", "2013/1/30",
		                "2013/1/31", "2013/2/1", "2013/2/4", "2013/2/5", "2013/2/6", 
		                "2013/2/7", "2013/2/8", "2013/2/18", "2013/2/19", "2013/2/20", 
		                "2013/2/21", "2013/2/22", "2013/2/25", "2013/2/26", "2013/2/27", 
		                "2013/2/28", "2013/3/1", "2013/3/4", "2013/3/5", "2013/3/6", 
		                "2013/3/7", "2013/3/8", "2013/3/11", "2013/3/12", "2013/3/13", 
		                "2013/3/14", "2013/3/15", "2013/3/18", "2013/3/19", "2013/3/20", 
		                "2013/3/21", "2013/3/22", "2013/3/25", "2013/3/26", "2013/3/27", 
		                "2013/3/28", "2013/3/29", "2013/4/1", "2013/4/2", "2013/4/3", 
		                "2013/4/8", "2013/4/9", "2013/4/10", "2013/4/11", "2013/4/12", 
		                "2013/4/15", "2013/4/16", "2013/4/17", "2013/4/18", "2013/4/19", 
		                "2013/4/22", "2013/4/23", "2013/4/24", "2013/4/25", "2013/4/26", 
		                "2013/5/2", "2013/5/3", "2013/5/6", "2013/5/7", "2013/5/8", 
		                "2013/5/9", "2013/5/10", "2013/5/13", "2013/5/14", "2013/5/15", 
		                "2013/5/16", "2013/5/17", "2013/5/20", "2013/5/21", "2013/5/22", 
		                "2013/5/23", "2013/5/24", "2013/5/27", "2013/5/28", "2013/5/29", 
		                "2013/5/30", "2013/5/31", "2013/6/3", "2013/6/4", "2013/6/5", 
		                "2013/6/6", "2013/6/7", "2013/6/13"
		            ]
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value',
		            scale:true,
		            boundaryGap: [0.01, 0.01]
		        }
		    ],
		    series : [
		        {
		            name:'上证指数',
		            type:'k',
		            data:[ // 开盘，收盘，最低，最高
		                [2320.26,2302.6,2287.3,2362.94],
		                [2300,2291.3,2288.26,2308.38],
		                [2295.35,2346.5,2295.35,2346.92],
		                [2347.22,2358.98,2337.35,2363.8],
		                [2360.75,2382.48,2347.89,2383.76],
		                [2383.43,2385.42,2371.23,2391.82],
		                [2377.41,2419.02,2369.57,2421.15],
		                [2425.92,2428.15,2417.58,2440.38],
		                [2411,2433.13,2403.3,2437.42],
		                [2432.68,2434.48,2427.7,2441.73],
		                [2430.69,2418.53,2394.22,2433.89],
		                [2416.62,2432.4,2414.4,2443.03],
		                [2441.91,2421.56,2415.43,2444.8],
		                [2420.26,2382.91,2373.53,2427.07],
		                [2383.49,2397.18,2370.61,2397.94],
		                [2378.82,2325.95,2309.17,2378.82],
		                [2322.94,2314.16,2308.76,2330.88],
		                [2320.62,2325.82,2315.01,2338.78],
		                [2313.74,2293.34,2289.89,2340.71],
		                [2297.77,2313.22,2292.03,2324.63],
		                [2322.32,2365.59,2308.92,2366.16],
		                [2364.54,2359.51,2330.86,2369.65],
		                [2332.08,2273.4,2259.25,2333.54],
		                [2274.81,2326.31,2270.1,2328.14],
		                [2333.61,2347.18,2321.6,2351.44],
		                [2340.44,2324.29,2304.27,2352.02],
		                [2326.42,2318.61,2314.59,2333.67],
		                [2314.68,2310.59,2296.58,2320.96],
		                [2309.16,2286.6,2264.83,2333.29],
		                [2282.17,2263.97,2253.25,2286.33],
		                [2255.77,2270.28,2253.31,2276.22],
		                [2269.31,2278.4,2250,2312.08],
		                [2267.29,2240.02,2239.21,2276.05],
		                [2244.26,2257.43,2232.02,2261.31],
		                [2257.74,2317.37,2257.42,2317.86],
		                [2318.21,2324.24,2311.6,2330.81],
		                [2321.4,2328.28,2314.97,2332],
		                [2334.74,2326.72,2319.91,2344.89],
		                [2318.58,2297.67,2281.12,2319.99],
		                [2299.38,2301.26,2289,2323.48],
		                [2273.55,2236.3,2232.91,2273.55],
		                [2238.49,2236.62,2228.81,2246.87],
		                [2229.46,2234.4,2227.31,2243.95],
		                [2234.9,2227.74,2220.44,2253.42],
		                [2232.69,2225.29,2217.25,2241.34],
		                [2196.24,2211.59,2180.67,2212.59],
		                [2215.47,2225.77,2215.47,2234.73],
		                [2224.93,2226.13,2212.56,2233.04],
		                [2236.98,2219.55,2217.26,2242.48],
		                [2218.09,2206.78,2204.44,2226.26],
		                [2199.91,2181.94,2177.39,2204.99],
		                [2169.63,2194.85,2165.78,2196.43],
		                [2195.03,2193.8,2178.47,2197.51],
		                [2181.82,2197.6,2175.44,2206.03],
		                [2201.12,2244.64,2200.58,2250.11],
		                [2236.4,2242.17,2232.26,2245.12],
		                [2242.62,2184.54,2182.81,2242.62],
		                [2187.35,2218.32,2184.11,2226.12],
		                [2213.19,2199.31,2191.85,2224.63],
		                [2203.89,2177.91,2173.86,2210.58],
		                [2170.78,2174.12,2161.14,2179.65],
		                [2179.05,2205.5,2179.05,2222.81],
		                [2212.5,2231.17,2212.5,2236.07],
		                [2227.86,2235.57,2219.44,2240.26],
		                [2242.39,2246.3,2235.42,2255.21],
		                [2246.96,2232.97,2221.38,2247.86],
		                [2228.82,2246.83,2225.81,2247.67],
		                [2247.68,2241.92,2231.36,2250.85],
		                [2238.9,2217.01,2205.87,2239.93],
		                [2217.09,2224.8,2213.58,2225.19],
		                [2221.34,2251.81,2210.77,2252.87],
		                [2249.81,2282.87,2248.41,2288.09],
		                [2286.33,2299.99,2281.9,2309.39],
		                [2297.11,2305.11,2290.12,2305.3],
		                [2303.75,2302.4,2292.43,2314.18],
		                [2293.81,2275.67,2274.1,2304.95],
		                [2281.45,2288.53,2270.25,2292.59],
		                [2286.66,2293.08,2283.94,2301.7],
		                [2293.4,2321.32,2281.47,2322.1],
		                [2323.54,2324.02,2321.17,2334.33],
		                [2316.25,2317.75,2310.49,2325.72],
		                [2320.74,2300.59,2299.37,2325.53],
		                [2300.21,2299.25,2294.11,2313.43],
		                [2297.1,2272.42,2264.76,2297.1],
		                [2270.71,2270.93,2260.87,2276.86],
		                [2264.43,2242.11,2240.07,2266.69],
		                [2242.26,2210.9,2205.07,2250.63],
		                [2190.1,2148.35,2126.22,2190.1]
		            ]
		        }
		    ]
		};
		                    

	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option);
	
	
	
	
	
	
		// 基于准备好的dom，初始化echarts实例
		var myChart = echarts.init(document.getElementById('Content-Left'));

		// 指定图表的配置项和数据
		var option = {
			title : {
				text : '商品销售'
			},
			tooltip : {},
			legend : {
				right : 10,
				data : [ '销量' ]
			},
			xAxis : {
				data : [ "衬衫", "羊毛衫", "雪纺衫", "裤子", "高跟鞋", "袜子" ]
			},
			yAxis : {},
			series : [ {
				name : '销量',
				type : 'bar',
				data : [ 5, 20, 36, 10, 10, 20 ]
			} ]
		};

		// 使用刚指定的配置项和数据显示图表。
		myChart.setOption(option);

		// 基于准备好的dom，初始化echarts实例
		/* var myChart = echarts.init(document.getElementById('Header'));

		// 指定图表的配置项和数据
		var option = {
			title : {
				text : '商品销售'
			},
			tooltip : {},
			legend : {
				right : 10,
				data : [ '销量' ]
			},
			xAxis : {
				data : [ "衬衫", "羊毛衫", "雪纺衫", "裤子", "高跟鞋", "袜子" ]
			},
			yAxis : {},
			series : [ {
				name : '销量',
				type : 'bar',
				data : [ 5, 20, 36, 10, 10, 20 ]
			} ]
		}; */

		// 使用刚指定的配置项和数据显示图表。
		myChart.setOption(option);
		
		option = {
			    title : {
			        text: '浏览器占比变化',
			        subtext: '纯属虚构',
			        x:'right',
			        y:'bottom'
			    },
			    tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {c} ({d}%)"
			    },
			    legend: {
			        orient : 'vertical',
			        x : 'left',
			        data:['Chrome','Firefox','Safari','IE9+','IE8-']
			    },
			    toolbox: {
			        show : true,
			        feature : {
			            mark : {show: true},
			            dataView : {show: true, readOnly: false},
			            restore : {show: true},
			            saveAsImage : {show: true}
			        }
			    },
			    calculable : false,
			    series : (function (){
			        var series = [];
			        for (var i = 0; i < 30; i++) {
			            series.push({
			                name:'浏览器（数据纯属虚构）',
			                type:'pie',
			                itemStyle : {normal : {
			                    label : {show : i > 28},
			                    labelLine : {show : i > 28, length:20}
			                }},
			                radius : [i * 4 + 40, i * 4 + 43],
			                data:[
			                    {value: i * 128 + 80,  name:'Chrome'},
			                    {value: i * 64  + 160,  name:'Firefox'},
			                    {value: i * 32  + 320,  name:'Safari'},
			                    {value: i * 16  + 640,  name:'IE9+'},
			                    {value: i * 8  + 1280, name:'IE8-'}
			                ]
			            })
			        }
			        series[0].markPoint = {
			            symbol:'emptyCircle',
			            symbolSize:series[0].radius[0],
			            effect:{show:true,scaleSize:12,color:'rgba(250,225,50,0.8)',shadowBlur:10,period:30},
			            data:[{x:'50%',y:'50%'}]
			        };
			        return series;
			    })()
			};
			setTimeout(function (){
			    var _ZR = myChart.getZrender();
			    var TextShape = require('zrender/shape/Text');
			    // 补充千层饼
			    _ZR.addShape(new TextShape({
			        style : {
			            x : _ZR.getWidth() / 2,
			            y : _ZR.getHeight() / 2,
			            color: '#666',
			            text : '恶梦的过去',
			            textAlign : 'center'
			        }
			    }));
			    _ZR.addShape(new TextShape({
			        style : {
			            x : _ZR.getWidth() / 2 + 200,
			            y : _ZR.getHeight() / 2,
			            brushType:'fill',
			            color: 'orange',
			            text : '美好的未来',
			            textAlign : 'left',
			            textFont:'normal 20px 微软雅黑'
			        }
			    }));
			    _ZR.refresh();
			}, 2000);

			                    
		
		echarts.init(document.getElementById('Content-Right')).setOption(option);
		
		
		
		echarts.init(document.getElementById('Content-Leftdown')).setOption({
			series : {
				type : 'pie',
				data : [ {
					name : 'A',
					value : 1212
				}, {
					name : 'B',
					value : 2323
				}, {
					name : 'C',
					value : 1919
				} ]
			}
		});
		

		option = {
		    title : {
		        text: '预算 vs 开销（Budget vs spending）',
		        subtext: '纯属虚构'
		    },
		    tooltip : {
		        trigger: 'axis'
		    },
		    legend: {
		        orient : 'vertical',
		        x : 'right',
		        y : 'bottom',
		        data:['预算分配（Allocated Budget）','实际开销（Actual Spending）']
		    },
		    toolbox: {
		        show : true,
		        feature : {
		            mark : {show: true},
		            dataView : {show: true, readOnly: false},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    polar : [
		       {
		           indicator : [
		               { text: '销售（sales）', max: 6000},
		               { text: '管理（Administration）', max: 16000},
		               { text: '信息技术（Information Techology）', max: 30000},
		               { text: '客服（Customer Support）', max: 38000},
		               { text: '研发（Development）', max: 52000},
		               { text: '市场（Marketing）', max: 25000}
		            ]
		        }
		    ],
		    calculable : true,
		    series : [
		        {
		            name: '预算 vs 开销（Budget vs spending）',
		            type: 'radar',
		            data : [
		                {
		                    value : [4300, 10000, 28000, 35000, 50000, 19000],
		                    name : '预算分配（Allocated Budget）'
		                },
		                 {
		                    value : [5000, 14000, 28000, 31000, 42000, 21000],
		                    name : '实际开销（Actual Spending）'
		                }
		            ]
		        }
		    ]
		};
		                    

		echarts.init(document.getElementById('Content-Maindown')).setOption(option);
		
		

		
		                    
		
		echarts.init(document.getElementById('Content-Main1down')).setOption({
			series : {
				type : 'pie',
				data : [ {
					name : 'A',
					value : 1212
				}, {
					name : 'B',
					value : 2323
				}, {
					name : 'C',
					value : 1919
				} ]
			}
		});
		
		
		option = {
			    title : {
			        text: '浏览器占比变化',
			        subtext: '纯属虚构',
			        x:'right',
			        y:'bottom'
			    },
			    tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {c} ({d}%)"
			    },
			    legend: {
			        orient : 'vertical',
			        x : 'left',
			        data:['Chrome','Firefox','Safari','IE9+','IE8-']
			    },
			    toolbox: {
			        show : true,
			        feature : {
			            mark : {show: true},
			            dataView : {show: true, readOnly: false},
			            restore : {show: true},
			            saveAsImage : {show: true}
			        }
			    },
			    calculable : false,
			    series : (function (){
			        var series = [];
			        for (var i = 0; i < 30; i++) {
			            series.push({
			                name:'浏览器（数据纯属虚构）',
			                type:'pie',
			                itemStyle : {normal : {
			                    label : {show : i > 28},
			                    labelLine : {show : i > 28, length:20}
			                }},
			                radius : [i * 4 + 40, i * 4 + 43],
			                data:[
			                    {value: i * 128 + 80,  name:'Chrome'},
			                    {value: i * 64  + 160,  name:'Firefox'},
			                    {value: i * 32  + 320,  name:'Safari'},
			                    {value: i * 16  + 640,  name:'IE9+'},
			                    {value: i * 8  + 1280, name:'IE8-'}
			                ]
			            })
			        }
			        series[0].markPoint = {
			            symbol:'emptyCircle',
			            symbolSize:series[0].radius[0],
			            effect:{show:true,scaleSize:12,color:'rgba(250,225,50,0.8)',shadowBlur:10,period:30},
			            data:[{x:'50%',y:'50%'}]
			        };
			        return series;
			    })()
			};
			setTimeout(function (){
			    var _ZR = myChart.getZrender();
			    var TextShape = require('zrender/shape/Text');
			    // 补充千层饼
			    _ZR.addShape(new TextShape({
			        style : {
			            x : _ZR.getWidth() / 2,
			            y : _ZR.getHeight() / 2,
			            color: '#666',
			            text : '恶梦的过去',
			            textAlign : 'center'
			        }
			    }));
			    _ZR.addShape(new TextShape({
			        style : {
			            x : _ZR.getWidth() / 2 + 200,
			            y : _ZR.getHeight() / 2,
			            brushType:'fill',
			            color: 'orange',
			            text : '美好的未来',
			            textAlign : 'left',
			            textFont:'normal 20px 微软雅黑'
			        }
			    }));
			    _ZR.refresh();
			}, 2000);

			                    

		


		var myChart = echarts.init(document.getElementById('Content-Main'));
		//app.title = '气泡图';

		var data = [
				[ [ 28604, 77, 17096869, 'Australia', 1990 ],
						[ 31163, 77.4, 27662440, 'Canada', 1990 ],
						[ 1516, 68, 1154605773, 'China', 1990 ],
						[ 13670, 74.7, 10582082, 'Cuba', 1990 ],
						[ 28599, 75, 4986705, 'Finland', 1990 ],
						[ 29476, 77.1, 56943299, 'France', 1990 ],
						[ 31476, 75.4, 78958237, 'Germany', 1990 ],
						[ 28666, 78.1, 254830, 'Iceland', 1990 ],
						[ 1777, 57.7, 870601776, 'India', 1990 ],
						[ 29550, 79.1, 122249285, 'Japan', 1990 ],
						[ 2076, 67.9, 20194354, 'North Korea', 1990 ],
						[ 12087, 72, 42972254, 'South Korea', 1990 ],
						[ 24021, 75.4, 3397534, 'New Zealand', 1990 ],
						[ 43296, 76.8, 4240375, 'Norway', 1990 ],
						[ 10088, 70.8, 38195258, 'Poland', 1990 ],
						[ 19349, 69.6, 147568552, 'Russia', 1990 ],
						[ 10670, 67.3, 53994605, 'Turkey', 1990 ],
						[ 26424, 75.7, 57110117, 'United Kingdom', 1990 ],
						[ 37062, 75.4, 252847810, 'United States', 1990 ] ],
				[ [ 44056, 81.8, 23968973, 'Australia', 2015 ],
						[ 43294, 81.7, 35939927, 'Canada', 2015 ],
						[ 13334, 76.9, 1376048943, 'China', 2015 ],
						[ 21291, 78.5, 11389562, 'Cuba', 2015 ],
						[ 38923, 80.8, 5503457, 'Finland', 2015 ],
						[ 37599, 81.9, 64395345, 'France', 2015 ],
						[ 44053, 81.1, 80688545, 'Germany', 2015 ],
						[ 42182, 82.8, 329425, 'Iceland', 2015 ],
						[ 5903, 66.8, 1311050527, 'India', 2015 ],
						[ 36162, 83.5, 126573481, 'Japan', 2015 ],
						[ 1390, 71.4, 25155317, 'North Korea', 2015 ],
						[ 34644, 80.7, 50293439, 'South Korea', 2015 ],
						[ 34186, 80.6, 4528526, 'New Zealand', 2015 ],
						[ 64304, 81.6, 5210967, 'Norway', 2015 ],
						[ 24787, 77.3, 38611794, 'Poland', 2015 ],
						[ 23038, 73.13, 143456918, 'Russia', 2015 ],
						[ 19360, 76.5, 78665830, 'Turkey', 2015 ],
						[ 38225, 81.4, 64715810, 'United Kingdom', 2015 ],
						[ 53354, 79.1, 321773631, 'United States', 2015 ] ] ];

		option = {
			/* backgroundColor : new echarts.graphic.RadialGradient(0.3, 0.3, 0.8,
					[ {
						offset : 0,
						color : '#f7f8fa'
					}, {
						offset : 1,
						color : '#cdd0d5'
					} ]), */
			title : {
				text : '1990 与 2015 年各国家人均寿命与 GDP'
			},
			legend : {
				right : 10,
				data : [ '1990', '2015' ]
			},
			xAxis : {
				splitLine : {
					lineStyle : {
						type : 'dashed'
					}
				}
			},
			yAxis : {
				splitLine : {
					lineStyle : {
						type : 'dashed'
					}
				},
				scale : true
			},
			series : [
					{
						name : '1990',
						data : data[0],
						type : 'scatter',
						symbolSize : function(data) {
							return Math.sqrt(data[2]) / 5e2;
						},
						label : {
							emphasis : {
								show : true,
								formatter : function(param) {
									return param.data[3];
								},
								position : 'top'
							}
						},
						itemStyle : {
							normal : {
								shadowBlur : 10,
								shadowColor : 'rgba(120, 36, 50, 0.5)',
								shadowOffsetY : 5,
								color : new echarts.graphic.RadialGradient(0.4,
										0.3, 1, [ {
											offset : 0,
											color : 'rgb(251, 118, 123)'
										}, {
											offset : 1,
											color : 'rgb(204, 46, 72)'
										} ])
							}
						}
					},
					{
						name : '2015',
						data : data[1],
						type : 'scatter',
						symbolSize : function(data) {
							return Math.sqrt(data[2]) / 5e2;
						},
						label : {
							emphasis : {
								show : true,
								formatter : function(param) {
									return param.data[3];
								},
								position : 'top'
							}
						},
						itemStyle : {
							normal : {
								shadowBlur : 10,
								shadowColor : 'rgba(25, 100, 150, 0.5)',
								shadowOffsetY : 5,
								color : new echarts.graphic.RadialGradient(0.4,
										0.3, 1, [ {
											offset : 0,
											color : 'rgb(129, 227, 238)'
										}, {
											offset : 1,
											color : 'rgb(25, 183, 207)'
										} ])
							}
						}
					} ]
		};
		myChart.setOption(option);
		
		
		
		var myChart = echarts.init(document.getElementById('Content-Rightdown'));
		//app.title = '气泡图';

		var data = [
				[ [ 28604, 77, 17096869, 'Australia', 1990 ],
						[ 31163, 77.4, 27662440, 'Canada', 1990 ],
						[ 1516, 68, 1154605773, 'China', 1990 ],
						[ 13670, 74.7, 10582082, 'Cuba', 1990 ],
						[ 28599, 75, 4986705, 'Finland', 1990 ],
						[ 29476, 77.1, 56943299, 'France', 1990 ],
						[ 31476, 75.4, 78958237, 'Germany', 1990 ],
						[ 28666, 78.1, 254830, 'Iceland', 1990 ],
						[ 1777, 57.7, 870601776, 'India', 1990 ],
						[ 29550, 79.1, 122249285, 'Japan', 1990 ],
						[ 2076, 67.9, 20194354, 'North Korea', 1990 ],
						[ 12087, 72, 42972254, 'South Korea', 1990 ],
						[ 24021, 75.4, 3397534, 'New Zealand', 1990 ],
						[ 43296, 76.8, 4240375, 'Norway', 1990 ],
						[ 10088, 70.8, 38195258, 'Poland', 1990 ],
						[ 19349, 69.6, 147568552, 'Russia', 1990 ],
						[ 10670, 67.3, 53994605, 'Turkey', 1990 ],
						[ 26424, 75.7, 57110117, 'United Kingdom', 1990 ],
						[ 37062, 75.4, 252847810, 'United States', 1990 ] ],
				[ [ 44056, 81.8, 23968973, 'Australia', 2015 ],
						[ 43294, 81.7, 35939927, 'Canada', 2015 ],
						[ 13334, 76.9, 1376048943, 'China', 2015 ],
						[ 21291, 78.5, 11389562, 'Cuba', 2015 ],
						[ 38923, 80.8, 5503457, 'Finland', 2015 ],
						[ 37599, 81.9, 64395345, 'France', 2015 ],
						[ 44053, 81.1, 80688545, 'Germany', 2015 ],
						[ 42182, 82.8, 329425, 'Iceland', 2015 ],
						[ 5903, 66.8, 1311050527, 'India', 2015 ],
						[ 36162, 83.5, 126573481, 'Japan', 2015 ],
						[ 1390, 71.4, 25155317, 'North Korea', 2015 ],
						[ 34644, 80.7, 50293439, 'South Korea', 2015 ],
						[ 34186, 80.6, 4528526, 'New Zealand', 2015 ],
						[ 64304, 81.6, 5210967, 'Norway', 2015 ],
						[ 24787, 77.3, 38611794, 'Poland', 2015 ],
						[ 23038, 73.13, 143456918, 'Russia', 2015 ],
						[ 19360, 76.5, 78665830, 'Turkey', 2015 ],
						[ 38225, 81.4, 64715810, 'United Kingdom', 2015 ],
						[ 53354, 79.1, 321773631, 'United States', 2015 ] ] ];

		option = {
			/* backgroundColor : new echarts.graphic.RadialGradient(0.3, 0.3, 0.8,
					[ {
						offset : 0,
						color : '#f7f8fa'
					}, {
						offset : 1,
						color : '#cdd0d5'
					} ]), */
			title : {
				text : '1990 与 2015 年各国家人均寿命与 GDP'
			},
			legend : {
				right : 10,
				data : [ '1990', '2015' ]
			},
			xAxis : {
				splitLine : {
					lineStyle : {
						type : 'dashed'
					}
				}
			},
			yAxis : {
				splitLine : {
					lineStyle : {
						type : 'dashed'
					}
				},
				scale : true
			},
			series : [
					{
						name : '1990',
						data : data[0],
						type : 'scatter',
						symbolSize : function(data) {
							return Math.sqrt(data[2]) / 5e2;
						},
						label : {
							emphasis : {
								show : true,
								formatter : function(param) {
									return param.data[3];
								},
								position : 'top'
							}
						},
						itemStyle : {
							normal : {
								shadowBlur : 10,
								shadowColor : 'rgba(120, 36, 50, 0.5)',
								shadowOffsetY : 5,
								color : new echarts.graphic.RadialGradient(0.4,
										0.3, 1, [ {
											offset : 0,
											color : 'rgb(251, 118, 123)'
										}, {
											offset : 1,
											color : 'rgb(204, 46, 72)'
										} ])
							}
						}
					},
					{
						name : '2015',
						data : data[1],
						type : 'scatter',
						symbolSize : function(data) {
							return Math.sqrt(data[2]) / 5e2;
						},
						label : {
							emphasis : {
								show : true,
								formatter : function(param) {
									return param.data[3];
								},
								position : 'top'
							}
						},
						itemStyle : {
							normal : {
								shadowBlur : 10,
								shadowColor : 'rgba(25, 100, 150, 0.5)',
								shadowOffsetY : 5,
								color : new echarts.graphic.RadialGradient(0.4,
										0.3, 1, [ {
											offset : 0,
											color : 'rgb(129, 227, 238)'
										}, {
											offset : 1,
											color : 'rgb(25, 183, 207)'
										} ])
							}
						}
					} ]
		};
		myChart.setOption(option);
	</script>
</body>


</html>