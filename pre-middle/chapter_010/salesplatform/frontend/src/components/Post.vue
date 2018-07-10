<template>
  <b-container>
    <slot>
      <article class="view">
        <div class="article-wrapper">
          <div class="post-pic">
            <div class="post-thumbnail">
              <img :src="post.pictures[0]
              ? 'pic?post=' + post.id + '&pic=' + post.pictures[0]
              : '/assets/noimage_thumbnail.png'"
                   :alt="post.car.carModel.manufacture.name">
              <span class="ribbon-featured" v-show="!post.isActive">
                <strong class="ribbon">Sold Out</strong>
              </span>
            </div>
          </div>
          <div class="post-content">
            <h2 class="post-title">{{
              post.car.carModel.manufacture.name }} {{
              post.car.carModel.name }}</h2>
            <sup>{{ post.publishDate }}</sup>
            <p class="post-description">{{ post.content }}</p>
            <ul class="post-attributes">
              <li>
                <span class="car-year">{{ post.car.year }}</span>
                <span
                  class="car-mileage">{{ post.car.mileage }} km</span>
                <span
                  class="car-fuel">{{ post.car.fuel.description }}</span>
                <span
                  class="car-enginesize">{{ post.car.engineSize }} cc</span>
                <span
                  class="car-body">{{ post.car.body.description }}</span>
                <span
                  class="car-category">{{ post.car.category.description }}</span>
                <span
                  class="car-transmission">{{ post.car.transmission.description }}</span>
              </li>
            </ul>
            <span class="post-price">
              <strong class="h3-responsive">&#36;{{ post.price }}</strong>
            </span>
          </div>

          <input v-if="auth" class="toggle" type="checkbox"
                 v-model="post.isActive" @click="carSold(post)">
        </div>
      </article>
    </slot>
  </b-container>
</template>

<script>
import axios from 'axios'
import router from '../router'

export default {
  props: ['post'],
  computed: {
    auth () {
      return this.$store.getters.isAuthenticated &&
        this.post.user.id === parseInt(localStorage.getItem('userId'), 10)
    }
  },
  methods: {
    carSold (advert) {
      const post = {
        car: advert.car,
        content: advert.content,
        id: advert.id,
        isActive: !advert.isActive,
        price: advert.price,
        publishDate: advert.publishDate,
        user: advert.user
      }
      const formdata = new FormData()
      formdata.append('postad', JSON.stringify(post))
      axios.post('/editad', formdata,
        {
          headers: {'Authorization': this.$store.getters.authToken}
        })
        .then(res => {
          router.replace('/')
        })
        .catch(error => console.log(error))
    }
  }
}
</script>

<style scoped>
  b-list-group-item {
    border: none;
  }

  .post-list > li {
    display: inline;
  }

  .article-wrapper {
    display: block;
    color: #3c3241;
    padding-top: 12px;
  }

  article {
    border-bottom: 1px solid #f1f0f1;
    display: block;
  }

  .post-pic {
    width: 120px;
    margin-right: 16px;
    float: left;
  }

  .post-thumbnail {
    width: 100%;
    float: left;
    height: 92px;
    margin-bottom: 8px;
    overflow: hidden;
    position: relative;
    border: 1px solid #d8d6d9;
  }

  .post-thumbnail > img {
    width: auto;
    max-width: inherit;
    position: absolute;
    height: 100%;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
    display: block;
  }

  .post-content {
    min-height: 92px;
    overflow: hidden;
  }

  .post-title {
    display: block;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    height: auto;
    font-size: 17px;
    color: #3997ba;
    margin-bottom: 0;
  }

  .post-description {
    margin-right: 20%;
    margin-bottom: 8px;
    text-align: justify;
    overflow: hidden;
    position: relative;
    display: block;
    color: #8a848d;
    font-size: 14px;
  }

  .post-attributes {
    margin: 0;
    margin-bottom: 8px;
    list-style: none;
    padding: 0;
  }

  .post-attributes > li {
    display: inline;
    margin-right: 4px;
    list-style: none;
  }

  span {
    line-height: 21px;
  }

  .post-attributes > li > span {
    color: #3c3241;
  }

  .post-attributes > li > span::after {
    content: "|";
    padding-left: 10px;
    font-weight: 700;
  }

  .post-attributes > li > span:last-child::after {
    content: "";
    padding: 0;
  }

  .post-price {
    float: left;
    line-height: 21px;
    padding-bottom: 8px;
    vertical-align: bottom;
  }

  .h3-responsive {
    font-size: 18px;
    margin-bottom: 8px;
    font-weight: 700;
  }

  .ribbon-featured {
    position: absolute;
    top: 0;
    left: 0;
    overflow: hidden;
    bottom: 0;
    right: 0;
  }

  .ribbon-featured .ribbon {
    background-color: #fff;
    color: #e57373;
    box-shadow: 0 0 2px 0 rgba(0, 0, 0, .2);
    text-transform: uppercase;
    display: block;
    text-align: center;
    top: 13px;
    left: -33px;
    font-weight: 700;
    width: 112px;
    padding: 3px 0;
    position: relative;
    font-size: 10px;
    transform: rotate(-45deg);
  }
</style>
