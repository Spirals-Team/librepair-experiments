<template>
  <div id="register">
    <div class="register-form">
      <b-form @submit.prevent="onSubmit">
        <b-form-group id="email"
                      label="Mail:"
                      label-for="emailInput">
          <b-form-input id="emailInput"
                        type="email"
                        v-model="user.email"
                        required
                        placeholder="Enter email">
          </b-form-input>
        </b-form-group>
        <b-form-group id="password"
                      label="Password:"
                      label-for="passwordInput">
          <b-form-input id="passwordInput"
                        type="password"
                        v-model="user.password"
                        required
                        placeholder="Enter password">
          </b-form-input>
        </b-form-group>
        <b-form-group id="phone"
                      label="Phone:"
                      label-for="phoneInput">
          <b-form-input id="phoneInput"
                        type="text"
                        v-model="user.phone"
                        required
                        placeholder="+55-55-555-55-55">
          </b-form-input>
        </b-form-group>
        <div class="submit">
          <button type="submit">Register</button>
        </div>
      </b-form>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      user: {
        email: '',
        password: '',
        phone: ''
      }
    }
  },
  methods: {
    onSubmit () {
      const formData = {
        user: {
          email: this.user.email,
          passw: this.user.password,
          phone: this.user.phone
        }
      }
      console.log(formData)
      this.$store.dispatch('register', formData)
    }
  }
}
</script>

<style scoped>
  .register-form {
    width: 400px;
    margin: 30px auto;
    border: 1px solid #eee;
    padding: 20px;
    box-shadow: 0 2px 3px #ccc;
  }

  .input {
    margin: 10px auto;
  }

  .input label {
    display: block;
    color: #4e4e4e;
    margin-bottom: 6px;
  }

  .input.inline label {
    display: inline;
  }

  .input input {
    font: inherit;
    width: 100%;
    padding: 6px 12px;
    box-sizing: border-box;
    border: 1px solid #ccc;
  }

  .input.inline input {
    width: auto;
  }

  .input input:focus {
    outline: none;
    border: 1px solid #521751;
    background-color: #eee;
  }

  .input select {
    border: 1px solid #ccc;
    font: inherit;
  }

  .submit button {
    border: 1px solid #521751;
    color: #521751;
    padding: 10px 20px;
    font: inherit;
    cursor: pointer;
  }

  .submit button:hover,
  .submit button:active {
    background-color: #521751;
    color: white;
  }

  .submit button[disabled],
  .submit button[disabled]:hover,
  .submit button[disabled]:active {
    border: 1px solid #ccc;
    background-color: transparent;
    color: #ccc;
    cursor: not-allowed;
  }
</style>
