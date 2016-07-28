var controllers = angular.module('MainController', []);

controllers.controller('JmxController', ['$scope', '$http', '$rootScope', '$state', '$location', function($scope, $http, $rootScope, $state, $location) {

    $scope.navMainHeader = 'JMX Monitoring';
    /*$scope.threads = [
                        {'threadId':'1', 'threadName':'Thread-1', 'threadState':'Runnable'},
                        {'threadId':'2', 'threadName':'Thread-2', 'threadState':'BLOCKED'}
    ];*/

    // ----------- High-Chart settings ----------------------

    // Load the fonts
    Highcharts.createElement('link', {
       href: 'https://fonts.googleapis.com/css?family=Unica+One',
       rel: 'stylesheet',
       type: 'text/css'
    }, null, document.getElementsByTagName('head')[0]);

    Highcharts.theme = {
       colors: ["#2b908f", "#90ee7e", "#f45b5b", "#7798BF", "#aaeeee", "#ff0066", "#eeaaee",
          "#55BF3B", "#DF5353", "#7798BF", "#aaeeee"],
       chart: {
          backgroundColor: {
             linearGradient: { x1: 0, y1: 0, x2: 1, y2: 1 },
             stops: [
                [0, '#2a2a2b'],
                [1, '#3e3e40']
             ]
          },
          style: {
             fontFamily: "'Unica One', sans-serif"
          },
          plotBorderColor: '#606063'
       },
       title: {
          style: {
             color: '#E0E0E3',
             textTransform: 'uppercase',
             fontSize: '20px'
          }
       },
       subtitle: {
          style: {
             color: '#E0E0E3',
             textTransform: 'uppercase'
          }
       },
       xAxis: {
          gridLineColor: '#707073',
          labels: {
             style: {
                color: '#E0E0E3'
             }
          },
          lineColor: '#707073',
          minorGridLineColor: '#505053',
          tickColor: '#707073',
          title: {
             style: {
                color: '#A0A0A3'

             }
          }
       },
       yAxis: {
          gridLineColor: '#707073',
          labels: {
             style: {
                color: '#E0E0E3'
             }
          },
          lineColor: '#707073',
          minorGridLineColor: '#505053',
          tickColor: '#707073',
          tickWidth: 1,
          title: {
             style: {
                color: '#A0A0A3'
             }
          }
       },
       tooltip: {
          backgroundColor: 'rgba(0, 0, 0, 0.85)',
          style: {
             color: '#F0F0F0'
          }
       },
       plotOptions: {
          series: {
             dataLabels: {
                color: '#B0B0B3'
             },
             marker: {
                lineColor: '#333'
             }
          },
          boxplot: {
             fillColor: '#505053'
          },
          candlestick: {
             lineColor: 'white'
          },
          errorbar: {
             color: 'white'
          }
       },
       legend: {
          itemStyle: {
             color: '#E0E0E3'
          },
          itemHoverStyle: {
             color: '#FFF'
          },
          itemHiddenStyle: {
             color: '#606063'
          }
       },
       credits: {
          style: {
             color: '#666'
          }
       },
       labels: {
          style: {
             color: '#707073'
          }
       },

       drilldown: {
          activeAxisLabelStyle: {
             color: '#F0F0F3'
          },
          activeDataLabelStyle: {
             color: '#F0F0F3'
          }
       },

       navigation: {
          buttonOptions: {
             symbolStroke: '#DDDDDD',
             theme: {
                fill: '#505053'
             }
          }
       },

       // scroll charts
       rangeSelector: {
          buttonTheme: {
             fill: '#505053',
             stroke: '#000000',
             style: {
                color: '#CCC'
             },
             states: {
                hover: {
                   fill: '#707073',
                   stroke: '#000000',
                   style: {
                      color: 'white'
                   }
                },
                select: {
                   fill: '#000003',
                   stroke: '#000000',
                   style: {
                      color: 'white'
                   }
                }
             }
          },
          inputBoxBorderColor: '#505053',
          inputStyle: {
             backgroundColor: '#333',
             color: 'silver'
          },
          labelStyle: {
             color: 'silver'
          }
       },

       navigator: {
          handles: {
             backgroundColor: '#666',
             borderColor: '#AAA'
          },
          outlineColor: '#CCC',
          maskFill: 'rgba(255,255,255,0.1)',
          series: {
             color: '#7798BF',
             lineColor: '#A6C7ED'
          },
          xAxis: {
             gridLineColor: '#505053'
          }
       },

       scrollbar: {
          barBackgroundColor: '#808083',
          barBorderColor: '#808083',
          buttonArrowColor: '#CCC',
          buttonBackgroundColor: '#606063',
          buttonBorderColor: '#606063',
          rifleColor: '#FFF',
          trackBackgroundColor: '#404043',
          trackBorderColor: '#404043'
       },

       // special colors for some of the
       legendBackgroundColor: 'rgba(0, 0, 0, 0.5)',
       background2: '#505053',
       dataLabelsColor: '#B0B0B3',
       textColor: '#C0C0C0',
       contrastTextColor: '#F0F0F3',
       maskColor: 'rgba(255,255,255,0.3)'
    };

    // Apply the theme
    Highcharts.setOptions(Highcharts.theme);

    // ----------- High-Chart settings ----------------------

    $scope.threadCount = [];
    $scope.xAxisTimeLine = [];
    $scope.threadChartConfig = {
            options: {
                chart: {
                    type: 'line' //column
                }
            },
            series: [{
                data: $scope.threadCount,
                name: '# of Threads'
            }],
            xAxis: {
                categories: $scope.xAxisTimeLine,
                name: 'Time'
            },
            yAxis: {
                title: {
                    text: 'No. of Threads'
                },
                plotLines: [{
                    value: 0,
                    width: 1,
                    color: '#808080'
                }]
            },
            title: {
                text: 'Thread Monitoring'
            }
    };

    $scope.gcCollectionTime = [];
    $scope.xAxisGCTimeLine = [];
    $scope.gcChartConfig = {
            options: {
                chart: {
                    type: 'line' //column
                }
            },
            series: [{
                data: $scope.gcCollectionTime,
                name: 'GC Collection Time'
            }],
            xAxis: {
                categories: $scope.xAxisGCTimeLine,
                name: 'Time'
            },
            yAxis: {
                title: {
                    text: 'GC Time taken'
                },
                plotLines: [{
                    value: 0,
                    width: 1,
                    color: '#808080'
                }]
            },
            title: {
                text: 'GC Monitoring'
            }
    };

    $scope.memoryUsed = [];
    $scope.xAxisMemoryTimeLine = [];
    $scope.memoryChartConfig = {
            options: {
                chart: {
                    type: 'line' //column
                }
            },
            series: [{
                data: $scope.memoryUsed,
                name: 'Used Memory'
            }],
            yAxis: {
                title: {
                    text: 'Memory'
                },
                plotLines: [{
                    value: 0,
                    width: 1,
                    color: '#808080'
                }]
            },
            xAxis: {
                categories: $scope.xAxisMemoryTimeLine,
                name: 'Time'
            },
            title: {
                text: 'Memory Monitoring'
            },
            tooltip: {
                formatter: function () {
                    return '<b>' + this.series.name + '</b><br/>' +
                        Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
                        Highcharts.numberFormat(this.y, 2);
                }
            }
    };

    $scope.threads = [];
    $scope.timeSinceLastEventForThread = -1;
    $scope.gcDetails = [];
    $scope.timeSinceLastEventForGC = -1;
    $scope.timeSinceLastEventForMemory = -1;
    $scope.gcCollectionCount = 0;
    $scope.maxHeapMemory = 0;
    $scope.timeInterval = 0;
    if(typeof(EventSource) != undefined) {
        console.log('EventSource invoked...');
        var sse = new EventSource('/jmx/stream/mapReduce');
        sse.onmessage = function(event) {

            //if(event.name == 'thread-details') {
            if(event.data.startsWith('{"threadCount":')) {
                var parsedData = JSON.parse(event.data);

                $scope.threads = parsedData.threadMetaInfoList;
                $scope.threadCount.push(parsedData.threadCount);

                $scope.timeSinceLastEventForThread = event.timeStamp;
                var nowDate = (new Date()).getTime();
                var afterIntervalAdded = new Date(nowDate + event.timeStamp);

                $scope.xAxisTimeLine.push(afterIntervalAdded.getHours() + ":" +afterIntervalAdded.getMinutes() + ":" +afterIntervalAdded.getSeconds());
            //} else if(event.name == 'gc-details') {
            } else if(event.data.startsWith('{"gcDetailList":')) {
                 var parsedData = JSON.parse(event.data);

                 $scope.gcDetails = parsedData.gcDetailList;

                 var collectionTime = 0;
                 var collectionCount = 0;
                 angular.forEach($scope.gcDetails, function(value, index) {
                    collectionTime = value.collectionTime;
                    collectionCount = collectionCount + value.collectionCount;
                 });
                 $scope.gcCollectionCount = collectionCount;

                 $scope.gcCollectionTime.push(collectionTime);

                 $scope.timeSinceLastEventForGC = event.timeStamp;
                 var nowDate = (new Date()).getTime();
                 var afterIntervalAdded = new Date(nowDate + event.timeStamp);

                 $scope.xAxisGCTimeLine.push(afterIntervalAdded.getHours() + ":" +afterIntervalAdded.getMinutes() + ":" +afterIntervalAdded.getSeconds());
            } else if(event.data.startsWith('{"usedMemory":')) {
                 var parsedData = JSON.parse(event.data);

                 $scope.memoryDetails = parsedData;

                 $scope.timeSinceLastEventForMemory = event.timeStamp;
                 var nowDate = (new Date()).getTime();
                 var afterIntervalAdded = new Date(nowDate + event.timeStamp);

                 //if($scope.timeInterval == 0 || $scope.timeInterval % 5 == 0) {
                 //   $scope.xAxisMemoryTimeLine.push(afterIntervalAdded.getHours() + ":" +afterIntervalAdded.getMinutes() + ":" +afterIntervalAdded.getSeconds());
                 //}
                 //$scope.timeInterval = $scope.timeInterval + 1;
                 $scope.xAxisMemoryTimeLine.push(afterIntervalAdded.getHours() + ":" +afterIntervalAdded.getMinutes() + ":" +afterIntervalAdded.getSeconds());

                 $scope.memoryUsed.push($scope.memoryDetails.usedMemory);
                 $scope.maxHeapMemory = $scope.memoryDetails.maxMemory;
            }

            $scope.$apply();
        };
    } else {
        console.log('EventSource not installed!');
    }

//    $http.get({
//            url: '/thread/:threadId',
//            method: "GET",
//            params: {threadId: threadId}
//            }).then(function(response) {
//                console.log("ok /thread/stack -> "+response);
//            }, function(response) {
//                console.log("err /thread/stack -> "+response);
//            });

}]);
