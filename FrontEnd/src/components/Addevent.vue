<template>
  <div style="width:20%; margin:auto; margin-top: 20px;">
    <b-form @submit.prevent="addevent">
      <b-form-group id="exampleInputGroup1"
                    label="Event Name:"
                    label-for="exampleInput1">
        <b-form-input v-model="name"
                      type="text"
                      required
                      placeholder="Enter name">
        </b-form-input>
      </b-form-group>
      <b-form-group label="Date:"
                    label-for="exampleInput1">
        <b-form-input v-model="date"
                      type="date"
                      required
                      placeholder="Enter date">
        </b-form-input>
      </b-form-group>
      <form>
        <div class="row">
          <div class="col">
            <b-form-group label="Start time:"
                          label-for="exampleInput1">
              <b-form-input v-model="starttime"
                            type="time"
                            required
                            placeholder="Enter start">
              </b-form-input>
            </b-form-group>
          </div>
          <div class="col">
            <b-form-group label="End time:"
                          label-for="exampleInput1">
              <b-form-input v-model="endtime"
                            type="time"
                            required
                            placeholder="Enter end">
              </b-form-input>
            </b-form-group>
          </div>
        </div>
      </form>
      <b-form-group label="Description:"
                    label-for="exampleInput2">
        <b-form-input v-model="description"

                      placeholder="Enter description">
        </b-form-input>
      </b-form-group>

      <div>Tag: </div>
        <b-form-select label= "Tag:" v-model="tag" class="mb-3" required >
        <option value="Sport">Sport</option>
        <option value="Rest">Rest</option>
      </b-form-select>
      <b-button type="submit" variant="primary">Submit</b-button>
      <b-button type="reset" variant="danger">Reset</b-button>
    </b-form>
    <p>{{this.msg}}</p>
  </div>
</template>
<script type="application/json">
  export default{
    name: 'Event',
    data () {
      return {
        date: '',
        starttime: '',
        endtime: '',
        name: '',
        description: '',
        tag: '',
        msg: ''
      }

    },
    methods: {
      addevent() {
        var sta = (this.date + ' ' + this.starttime);
        var en = (this.date + ' ' + this.endtime);
        var datesta = new Date(sta); // some mock date
        var dateend = new Date(en);
        this.temp = datesta
        if(datesta.getTime() >= dateend.getTime()){
          window.alert("end time needs to be after start time");
          return;
        }
        let input = {"name": this.name, "startTime": String(datesta.getTime()),"endTime": String(dateend.getTime()), "description": this.description, "tag": this.tag,"pulses": []};
        input = JSON.stringify(input);
        let url = 'http://localhost:8081/users/addEvent'
        this.$http.post(url, input,{headers: {'Content-Type': 'application/json',
          'Authorization': localStorage.getItem('token')}} ).then((res) => {
            //check if event collides with another
            if(res["body"] == false){
              window.alert("colliding events");
              return;
            }
          console.log('Success', res);
        }, (err) => {
          console.log('Error: ', err);

        })
          this.msg = "Event Added!"
      }
    }
  }
</script>
