<script>
import {Line} from 'vue-chartjs'

export default { 
  name: 'Event', 
  extends: Line,
  data () {
    return {
      gradient: null,
      gradient2: null,
      name: ''
    }
  },
  methods:{
    graphClickEvent(event, array){
      var points = this.getElementAtEvent(event)
      },
       getTime (putName) {
            var avgList = [];
            var timeList = [];
            var id = this.$route.query.id;
            this.$http.post('http://localhost:8081/users/getEvent',{"id": id}
             ,{headers: {'Content-Type': 'application/json',
              'Authorization': localStorage.getItem('token'),}
            }).then((res) => {
              // res.body = array of event object
              var pulsesArr = res.body.pulses;
              var numofpulses = pulsesArr.length;
              var startTime = parseInt(res.body.startTime) + 60000
                for (var i = 0; i < numofpulses; i++) {
                  var date = new Date(startTime)
                  var hours = date.getHours()
                  hours = ("0" + hours).slice(-2);
                  var minutes = date.getMinutes()
                  minutes = ("0" + minutes).slice(-2);
                  var str = hours + ":" + minutes;
                  timeList.push(str);
                  startTime = startTime + 60000;
                }
                for (var i = 0; i < numofpulses; i++) {
                    var pulse = pulsesArr[i];
                    // this.avgList.push({label: evnt.name,y:evnt.pulseAverage});
                  avgList.push({y: pulse.value});
                }
            })
            return [avgList,timeList];
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
      // labels: ['16/12', '17/12', '18 /12', '19/12', '20/12', '21/12', '22/12', '23/12'],
      labels: this.getTime(this.putName)[1],
      datasets: [
        {
          label: 'Event',
          borderColor: '#05CBE1',
          pointBackgroundColor: 'white',
          pointBorderColor: 'white',
          borderWidth: 2,
          backgroundColor: this.gradient2,
          data: this.getTime(this.putName)[0]
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
                       // var evnt = 'Type: ' + data.datasets[0].data[tooltipItems.index].type;
                        return avg;
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