<template>
<div style="width:30%; margin:auto" >
  <p></p>
    <h1>Remove event</h1>
    <div v-if=!isempty v-cloak> 
        <b-form @submit.prevent="RemoveEvent">
        <b-form-select label= "Tag:" :options="this.eventsList" v-model="retvalue"  class="mb-3" required>
      </b-form-select>
            <b-button type="submit" variant="primary">Remove</b-button>
    </b-form>
      <p>{{this.msg}}</p>
  </div>
</div>
</template>

<script>
export default {
  name: 'RemoveEvent',
  data () {
  return {
    eventsList: [],
    retvalue: '',
    msg: '',
    isempty : true
  }
  },
  created: function(){
    this.getEvents();
  },
  methods:{
          getTimes(time){
              var date = new Date(parseInt(time))
                 var day = date.getDate()
                 var hours = date.getHours()
                 hours = ("0" + hours).slice(-2);
                 var minutes = date.getMinutes()
                 minutes = ("0" + minutes).slice(-2);
                 var str = hours + ":" + minutes
                 return str
          },
           getEvents () {
            this.$http.get('http://localhost:8081/users/getAllEvents'
             ,{headers: {'Content-Type': 'application/json',
              'Authorization': localStorage.getItem('token'),}
            }).then((res) => {
              var eventsArr = res.body;
                var arrayLength = eventsArr.length;
                if(arrayLength != 0) this.isempty = false;
                for (var i = 0; i < arrayLength; i++) {
                var date = new Date(parseInt(eventsArr[i].startTime))
                 var day = date.getDate()
                 var month =  date.getMonth()
                 var year = date.getFullYear()
                  var str = day + "." + (month + 1) + "." + year +" - " + this.getTimes(eventsArr[i].startTime) + ' - ' + this.getTimes(eventsArr[i].endTime)
                  this.eventsList.push( {value:res.body[i].id,text:res.body[i].name + ' ' + str});
                }    

            })
        },
        RemoveEvent(){
            this.$http.post('http://localhost:8081/users/deleteEvent',{"eventId": this.retvalue}
             ,{headers: {'Content-Type': 'application/json',
              'Authorization': localStorage.getItem('token'),}
            })
            this.msg = 'Event Removed!'
            // location.reload();
        }
  }
}
</script>
<style> [v-cloak] > * { display:none }
[v-cloak]::before { content: "loading…" } </style>