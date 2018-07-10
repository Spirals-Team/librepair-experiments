<template>
  <section class="carsalespostadd">
    <div class="postad-main">
      <b-form enctype="multipart/form-data" @submit="onSubmit"
              class="postad-form" name="newpostad">
        <b-form-row>
          <b-col class="postad-keyword" md="4" sm="12">
            <b-form-group id="manufacture"
                          label="Manufacture:"
                          label-for="manufactureInput">
              <b-form-select id="manufactureInput"
                             :options="manufactures"
                             required
                             @change="onChange($event)"
                             v-model="postad.car.carModel.manufacture.id">
              </b-form-select>
            </b-form-group>
          </b-col>
          <b-col class="postad-keyword" md="4" sm="12">
            <b-form-group id="carModel"
                          label="Car Model:"
                          label-for="carModelInput">
              <b-form-select id="carModelInput"
                             :options="carModelsManufacture"
                             required
                             v-model="postad.car.carModel.id">
              </b-form-select>
            </b-form-group>
          </b-col>
          <b-col class="postad-keyword" md="4" sm="12">
            <b-form-group id="category"
                          label="Category:"
                          label-for="categoryInput">
              <b-form-select id="categoryInput"
                             :options="categories"
                             required
                             v-model="postad.car.category.id">
              </b-form-select>
            </b-form-group>
          </b-col>
        </b-form-row>
        <b-form-row class="form-row">
          <b-col class="postad-keyword" md="4" sm="12">
            <b-form-group id="body"
                          label="Body Type:"
                          label-for="bodyInput">
              <b-form-select id="bodyInput"
                             :options="bodies"
                             required
                             v-model="postad.car.body.id">
              </b-form-select>
            </b-form-group>
          </b-col>
          <b-col class="postad-keyword" md="4" sm="12">
            <b-form-group id="fuel"
                          label="Fuel Type:"
                          label-for="fuelInput">
              <b-form-select id="fuelInput"
                             :options="fuels"
                             required
                             v-model="postad.car.fuel.id">
              </b-form-select>
            </b-form-group>
          </b-col>
          <b-col class="postad-keyword" md="4" sm="12">
            <b-form-group id="transmission"
                          label="Transmission Type:"
                          label-for="transmissionInput">
              <b-form-select id="transmissionInput"
                             :options="transmissions"
                             required
                             v-model="postad.car.transmission.id">
              </b-form-select>
            </b-form-group>
          </b-col>
        </b-form-row>
        <b-form-row class="form-row ">
          <b-col class="postad-keyword" md="4" sm="12">
            <b-form-group id="content"
                          label="Manufacture:"
                          label-for="contentArea">
              <b-form-textarea id="contentArea"
                               v-model="postad.content"
                               placeholder="Describe you car..."
                               :rows="3"
                               :max-rows="6">
              </b-form-textarea>
            </b-form-group>
          </b-col>
          <b-col class="postad-keyword" md="4" sm="12">
            <b-form-group id="price"
                          label="Enter the price (e.g. 5000):"
                          label-for="priceInput">
              <b-form-input id="priceInput"
                            v-model="postad.price"
                            type="number"
                            placeholder="5000" step="1" min="1"
                            autocomplete="off"></b-form-input>
            </b-form-group>
          </b-col>
          <b-col class="postad-keyword" md="4" sm="12">
            <b-form-group id="engineSize"
                          label="Enter the engine size (e.g. 1.2):"
                          label-for="engineSizeInput">
              <b-form-input id="engineSizeInput"
                            v-model="postad.car.engineSize"
                            type="number"
                            placeholder="1.2" step="0.1" min="0.1"
                            autocomplete="off"></b-form-input>
            </b-form-group>
          </b-col>
        </b-form-row>
        <b-form-row class="form-row ">
          <b-col class="postad-keyword" md="4" sm="12">
            <b-form-group id="mileage"
                          label="Enter the mileage (e.g. 65000):"
                          label-for="mileageInput">
              <b-form-input id="mileageInput"
                            v-model="postad.car.mileage"
                            type="number"
                            placeholder="65000" step="1" min="1"
                            autocomplete="off"></b-form-input>
            </b-form-group>
          </b-col>
          <b-col class="postad-keyword" md="4" sm="12">
            <b-form-group id="year"
                          label="Enter the year (e.g. 2015)*:"
                          label-for="yearInput">
              <b-form-input id="yearInput"
                            v-model="postad.car.year"
                            type="number" placeholder="2008" step="1" min="1900"
                            autocomplete="off"></b-form-input>
            </b-form-group>
          </b-col>
          <b-col class="postad-keyword" md="4" sm="12">
            <b-form-group id="pictures"
                          label="Selected file:"
                          label-for="picturesInput">{{ pictures[0] &&
              pictures[0].name }}
              <b-form-file id="picturesInput" v-model="pictures"
                           :state="Boolean(pictures[0])"
                           :multiple=true
                           name="pictures"
                           placeholder="Choose a file..."></b-form-file>
            </b-form-group>
          </b-col>
        </b-form-row>
        <b-form-row class="form-row">
          <b-col class="postad-keyword">
            <b-button type="submit" variant="primary">Post</b-button>
          </b-col>
        </b-form-row>
      </b-form>
    </div>
  </section>
</template>

<script>
import axios from 'axios'
import router from '../router'

export default {
  data () {
    return {
      selectedManufacture: 1,
      manufactures: [],
      carModels: [],
      categories: [],
      bodies: [],
      fuels: [],
      transmissions: [],
      postad: {
        content: '',
        price: 0.0,
        isActive: true,
        car: {
          body: {
            id: 1
          },
          category: {
            id: 1
          },
          fuel: {
            id: 1
          },
          carModel: {
            id: 1,
            manufacture: {
              id: 1
            }
          },
          transmission: {
            id: 1
          },
          engineSize: 0.0,
          mileage: 0,
          year: 2015
        },
        user: {
          email: '',
          phone:
            ''
        }
      },
      pictures: []
    }
  },
  created () {
    axios.get('/metadata',
      {
        headers: {'Authorization': this.$store.getters.authToken}
      }
    )
      .then(res => {
        console.log(res)
        const data = res.data
        this.manufactures = data.manufactures.map((val) => {
          return {value: val.id, text: val.name}
        }) || []
        this.carModels = data.carmodels || []
        this.categories = data.categories.map((val) => {
          return {value: val.id, text: val.description}
        }) || []
        this.bodies = data.bodies.map((val) => {
          return {value: val.id, text: val.description}
        }) || []
        this.fuels = data.fuels.map((val) => {
          return {value: val.id, text: val.description}
        }) || []
        this.transmissions = data.transmissions.map((val) => {
          return {value: val.id, text: val.description}
        }) || []
      })
      .catch(error => console.log(error))
  },
  computed: {
    carModelsManufacture: {
      get () {
        const that = this
        const models = this.carModels.filter((c) => {
          if (c.manufacture.id === parseInt(that.selectedManufacture, 10)) {
            return c
          }
        })
        return models.map((val) => {
          return {value: val.id, text: val.name}
        }) || []
      },
      set (event) {
        //
      }
    }
  },
  methods: {
    onSubmit () {
      event.preventDefault()
      const formdata = new FormData()
      this.pictures.forEach(function (value, idx) {
        formdata.append('pic' + idx, value, value.name)
      })
      formdata.append('postad', JSON.stringify(this.postad))
      axios.post('/postad', formdata,
        {
          headers: {'Authorization': this.$store.getters.authToken}
        })
        .then(res => {
          router.replace('/')
        })
        .catch(error => console.log(error))
    },
    onChange (value) {
      this.selectedManufacture = value
    }
  }
}
</script>

<style></style>
