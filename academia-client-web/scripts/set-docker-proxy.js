const fs = require('fs')
const path = require('path')

const packageJson = require('../package.json')

packageJson.proxy = 'http://academia-api:8080'

const packageJsonPath = path.resolve(__dirname, '..', 'package.json')

fs.writeFile(packageJsonPath, `${JSON.stringify(packageJson, null, 2)}\n`)
