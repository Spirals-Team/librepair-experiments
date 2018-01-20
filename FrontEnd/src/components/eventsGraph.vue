
<script>
import {Line} from 'vue-chartjs'
export default {
  name: 'Events',
  extends: Line,
  created: function () {
    this.getEvents();
        },
  data () {
    return {
      gradient: null,
      gradient2: null,
      datesList : [],
      avgList: [],
      isempty: true
    }
  },
  methods:{
    graphClickEvent(event, array){
      var points = this.getElementAtEvent(event)
      },
      getEvents () {
           this.$http.post('http://localhost:8081/users/getEvents',{
             "first": 1515103200000,
             "second": 1516399200000
           }
            ,{headers: {'Content-Type': 'application/json',
             'Authorization': localStorage.getItem('token'),}
           }).then((res) => {
             // res.body = array of event object
             var eventsArr = res.body;
               var arrayLength = eventsArr.length;
               for (var i = 0; i < arrayLength; i++) {
                 var date = new Date(parseInt(eventsArr[i].startTime))
                 var day = date.getDate()
                 var month =  date.getMonth()
                 var year = date.getFullYear()
                 var hours = date.getHours()
                 hours = ("0" + hours).slice(-2);
                 var minutes = date.getMinutes()
                 minutes = ("0" + minutes).slice(-2);
                 var str = day;
                 var str = day + "." + (month + 1) + "." + year +" - " + hours + ":" + minutes
                   this.datesList.push(str);
               }
               for (var i = 0; i < arrayLength; i++) {
                   var evnt = eventsArr[i];
                 this.avgList.push({label: evnt.name,y: evnt.pulseAverage ,tag: evnt.tag, id: evnt.id });
               }
           })
       }
 },
  mounted () {
    this.gradient = this.$refs.canvas.getContext('2d').createLinearGradient(0, 0, 0, 450)
    this.gradient2 = this.$refs.canvas.getContext('2d').createLinearGradient(0, 0, 0, 450)
    this.gradient.addColorStop(0, 'rgba(255, 0,0, 0.5)')
    this.gradient.addColorStop(0.5, 'rgba(255, 0, 0, 0.25)');
    this.gradient.addColorStop(1, 'rgba(255, 0, 0, 0)');
    this.gradient2.addColorStop(0, 'rgba(0, 231, 255, 0.9)')
    this.gradient2.addColorStop(0.5, 'rgba(0, 231, 255, 0.35)');
    this.gradient2.addColorStop(1, 'rgba(0, 231, 255, 0)');
    this.renderChart({
      labels: this.datesList,
      datasets: [
        {
          label: 'Events',
          borderColor: 'black',
          pointBackgroundColor: 'white',
          pointBorderColor: 'gray',
          borderWidth: 1,
          backgroundColor: 'rgba(51,122,183,0.7)',
          data: this.avgList
         },
      ]
    ,
     options: {
        scales: {
            xAxes: [{
                ticks: {
                    beginAtZero:true
                }
            }]
        }
    }
  }
    //
     ,{ onClick: function(event){
      var activePoints = this.getElementAtEvent(event)
       var firstPoint = activePoints[0];
  if(firstPoint !== undefined){
    var label = this.data.labels[firstPoint._index];
    var value = this.data.datasets[firstPoint._datasetIndex].data[firstPoint._index];
    var location = "eventGraph?id=" + value.id;
     window.open(location, 'event graph', "height=500px,width=600px");
    }
       }
      , responsive: true, maintainAspectRatio: false,fontColor: '#66226',
    tooltips: {
                enabled: true,
                mode: 'single',
                callbacks: {
                   title: function(tooltipItems, data) {
                    var evnt = data.datasets[0].data[tooltipItems[0].index].label;
                    return evnt
                  },
                    label: function(tooltipItems, data) {
                      function restHeartStats(rate, age){
                        if(age < 2){
                          if(rate <= 100){
                            return "BELOW AVERAGE";
                          }
                          if(rate >= 170){
                            return "ABOVE AVERAGE";
                          }
                        }
                        else if(age > 1 && age <= 11){
                          if(rate <= 60){
                            return "BELOW AVERAGE";
                          }
                          if(rate >= 110){
                            return "ABOVE AVERAGE";
                          }
                        }
                        else if(age > 11){
                          if(rate < 40){
                            return "DANGEROUS";
                          }
                          if(rate < 60 && rate >= 40){
                            return "ATHLETE";
                          }
                          if(rate > 100){
                            return "ABOVE AVERAGE";
                          }
                        }
                        return "AVERAGE";
                      };
                      function sportHeartStats(rate, age){
                        var maxRate = 220 - age;
                        var upper = maxRate * 0.85;
                        var lower = maxRate * 0.5
                        if(rate < lower){
                          return "BELOW AVERAGE";
                        }
                        if(rate > upper){
                          return "ABOVE AVERAGE";
                        }
                        return "AVERAGE";
                      };
                      function heartStats(type, rate, age){
                        if(type == "Rest"){
                          return restHeartStats(rate, age);
                        }
                        return sportHeartStats(rate, age);
                      };
                       var avg = 'Average heart: ' + [tooltipItems.yLabel];
                       var evnt = 'Type: ' + data.datasets[0].data[tooltipItems.index].tag;
                       var stats = heartStats(data.datasets[0].data[tooltipItems.index].tag, [tooltipItems.yLabel], 30);
                        return [avg,evnt,stats];
                    },
                }
            }
          })
}
}
</script>