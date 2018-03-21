var webpack = require('karma-webpack');
var path = require('path');

var BUILD_DIR = path.resolve(__dirname, 'public');
var NODE_DIR = path.resolve(__dirname, 'node_modules');
var TEST_DIR = path.resolve(__dirname, '__tests__');

process.env.CHROMIUM_BIN = require('puppeteer').executablePath()

const WATCH = process.argv.indexOf('--watch') > -1;

module.exports = function (config) {
  config.set({
    autoWatch: WATCH,
    browsers: [
      'ChromiumHeadlessNoSandbox'
    ],
    coverageIstanbulReporter: {
      fixWebpackSourcePaths: true,
      reports: [
        'lcovonly',
        'html',
        'text-summary'
      ]
    },
    customLaunchers: {
      ChromiumHeadlessNoSandbox: {
        base: 'ChromiumHeadless',
        flags: [
          '--no-sandbox'
        ]
      }
    },
    files: [
      'node_modules/babel-polyfill/browser.js',
      'tests.webpack.js'
    ],
    frameworks: [
      'chai',
      'mocha'
    ],
    preprocessors: {
      'tests.webpack.js': [
        'webpack',
        'sourcemap'
      ]
    },
    remapIstanbulReporter: {
      reports: {
        lcovonly: '../../../target/test-results/coverage/report-lcov/lcov.info',
        html: '../../../target/test-results/coverage'
      }
    },
    reporters: ['progress', 'mocha', 'coverage-istanbul', 'karma-remap-istanbul'],
    singleRun: !WATCH,
    webpack: {
      devtool: 'inline-source-map',
      module: {
        rules: [
          {
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
            loader: "style-loader!css-loader",
            test: /\.css$/
          }, {
            loader: "file-loader?publicPath=public/",
            test: /\.(jpe?g|png|svg|ai)$/
          }, {
            loader: 'url-loader?limit=100000',
            test: /\.(woff|woff2|eot|ttf)$/
          }, {
            enforce: 'post',
            exclude: [
              BUILD_DIR,
              NODE_DIR,
              TEST_DIR
            ],
            loader: 'istanbul-instrumenter-loader',
            test: /\.js$/,
            options: {
              plugins: [
                'transform-object-rest-spread'
              ],
              presets: [
                'env',
                'react'
              ]
            }
          }
        ]
      }
    }
  });
};