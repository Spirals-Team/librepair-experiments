var webpack = require('webpack');
var path = require('path');

var BUILD_DIR = path.resolve(__dirname, 'public');
var NODE_DIR = path.resolve(__dirname, 'node_modules');
var TEST_DIR = path.resolve(__dirname, '__tests__');

var APP = path.resolve(__dirname, 'app');
var COMMON_UTILS = path.resolve(__dirname, 'common');
var AUTHORIZE_APP = path.resolve(__dirname, 'authorize');
var SIGNIN_APP = path.resolve(__dirname, 'signin');
var SIGNUP_APP = path.resolve(__dirname, 'signup');

module.exports = {
  entry: {
    app: APP,
    authorize: AUTHORIZE_APP,
    signin: SIGNIN_APP,
    signup: SIGNUP_APP
  },
  module: {
    rules: [
      {
        exclude: [
          NODE_DIR,
          TEST_DIR
        ],
        include: [
          APP,
          COMMON_UTILS,
          AUTHORIZE_APP,
          SIGNIN_APP,
          SIGNUP_APP
        ],
        loader: 'babel-loader',
        test: /\.js$/,
        options: {
          plugins: [
            'transform-flow-strip-types',
            'transform-object-rest-spread'
          ],
          presets: [
            'env',
            'react',
            'flow'
          ]
        }
      }, {
        loader: 'style-loader!css-loader',
        test: /\.css$/
      }, {
        loader: 'file-loader?publicPath=public/',
        test: /\.(jpe?g|png|svg|ai)$/
      }, {
        loader: 'url-loader?limit=100000',
        test: /\.(woff|woff2|eot|ttf)$/
      }
    ]
  },
  output: {
    path: BUILD_DIR,
    filename: '[name].js'
  },
  plugins: [
    new webpack.ProvidePlugin({
      $: 'jquery',
      jQuery: 'jquery'
    })
  ]
};