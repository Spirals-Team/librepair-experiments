<script>
import {Line} from 'vue-chartjs'

export default { 
  name: 'Chart', 
  extends: Line,
  data () {
    return {
      gradient: null,
      gradient2: null,
      datesList : [],
      avgList: []
    }
  },
  created: function () {
    this.getEvents();
        },
  methods:{
    graphClickEvent(event, array){
      var points = this.getElementAtEvent(event)
      console.log(points)
      },
       getEvents () {
            this.$http.get('http://localhost:8081/users/getAllEvents',{headers: {'Content-Type': 'application/json',
              'Authorization': localStorage.getItem('token')}
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
                  var str = day + "." + (month + 1) + "." + year +"//" + hours + ":" + minutes
                    this.datesList.push(str);
                }
                console.log(this.datesList)
            }
            , (err) => {
              console.log(err);
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
      labels: ['16/12', '17/12', '18 /12', '19/12', '20/12', '21/12', '22/12', '23/12'],
      datasets: [
        {
          label: 'Events',
          borderColor: '#05CBE1',
          pointBackgroundColor: 'white',
          pointBorderColor: 'white',
          borderWidth: 2,
          backgroundColor: this.gradient2,
          // data: [60, 55, 125, 65, 95, 60 ,64,123]
          data: [{label: 'Work with Adi',y: 60,id: 34},
                 {label: 'Working on project',y: 55},
                 {label: 'a a bug',y: 125},
                 {label: 'Going for a walk',y: 65},
                 {label: 'Having fun with our fitbit watch',y: 95},
                 {label: 'Soccer course',y: 60},
                 {label: 'Fixing a bug',y: 64},
                 {label: 'STILL Fixing a bug',y: 123}]
         },

        // ,{
         
        //   label: 'Eran',
        //   borderColor: '#FC2525',
        //   pointBackgroundColor: 'white',
        //   borderWidth: 2,
        //   pointBorderColor: 'white',
        //   backgroundColor: this.gradient,
        //   data: [65, 75, 50, 125, 65, 60, 65]
        // }
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
    // window.location.href = "/";  
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
                       var avg = 'Average heart: ' + [tooltipItems.yLabel];
                       var evnt = 'Event name: ' + data.datasets[0].data[tooltipItems.index].id;
                        return [avg,evnt];
                    }
        //             ,
        //             afterLabel: function(tooltipItems, data) {
        //   var evnt = 'Event name: ' + data.datasets[0].data[tooltipItems.index].label;
        //                 return evnt;
        // }
                }
            }
          })
}
}
</script>