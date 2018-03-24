var webpack = require('webpack');
var path = require('path');

var BUILD_DIR = path.resolve(__dirname, 'public');
var NODE_DIR = path.resolve(__dirname, 'node_modules');
var TEST_DIR = path.resolve(__dirname, '__tests__');

var APP_DIR = path.resolve(__dirname, 'app');

var config = {
  entry: APP_DIR + '/index.js',
  output: {
    path: BUILD_DIR,
    filename: 'bundle.js'
  },
  module: {
    rules: [
      {
        test: /\.js$/,
        exclude: [
          NODE_DIR,
          TEST_DIR
        ],
        include: [
          APP_DIR
        ],
        loader: 'babel-loader',
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
        test: /\.css$/,
        loader: "style-loader!css-loader"
      }, {
        test: /\.(jpe?g|png|svg|ai)$/,
        loader: "file-loader?publicPath=public/"
      }, {
        test: /\.(woff|woff2|eot|ttf)$/,
        loader: 'url-loader?limit=100000'
      }
    ]
  },
  plugins: [new webpack.ProvidePlugin({
    $: "jquery",
    jQuery: "jquery"
  })]
};

module.exports = config;