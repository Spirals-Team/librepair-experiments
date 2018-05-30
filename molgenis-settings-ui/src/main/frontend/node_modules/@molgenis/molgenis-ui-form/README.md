# molgenis-ui-form

[![Build Status](https://travis-ci.org/molgenis/molgenis-ui-form.svg?branch=master)](https://travis-ci.org/molgenis/molgenis-ui-form)
[![Known Vulnerabilities](https://snyk.io/test/github/molgenis/molgenis-ui-form/badge.svg?targetFile=package.json)](https://snyk.io/test/github/molgenis/molgenis-ui-form?targetFile=package.json)

> Library for generating HTML web forms

## Usage

### Install

```bash
# Add library using yarn.
yarn add @molgenis/molgenis-ui-form
```

### Use

```vue
<template>
    <form-component
      id="example-form"
      :formFields="formFields"
      :initialFormData="formData"
      :formState="formState"
      :options="options"
      @valueChanged="onValueChanged"
      @addOptionRequest="handleAddOptionRequest">
    </form-component>
</template>
  
<script>
  // Import form component
  import { FormComponent } from '@molgenis/molgenis-ui-form'
  
  // Import EntityToFormMapper
  import { EntityToFormMapper } from '@molgenis/molgenis-ui-form'
  
  export default {
    name: 'ExampleComponent',
    data () {
      return {
        formFields: [],
        initialFormData: {},
        formState: {},
        options: {
          showEyeButton: true
        }
      }
    },
    methods: {
      handleAddOptionRequest (addNewItemFunction) {
        // Handle the request to add a new item here
        const item = {
          id: 'new_item',
          label: 'New Item',
          value: 'new_item'
        }
        
        addNewItemFunction(item)
      },
      
      onValueChanged (formData) {
        // Do something with the updated formData
        console.log(formData)
      }
    },
    created () {
      // Generate a form from a MOLGENIS v2 API response 
      // Or create fields based on the specs (Form specifications)
      const form = EntityToFormMapper(metadata, items[0])
      this.formFields = form.formFields
      this.initialFormData = form.formData
    }
  }
</script>
```

__Note__: Whatever you pass to the FormComponent as formData object, 
the FormComponent makes a local copy with `Object.assign({}, formData)`.

If you want to react to data input, use the [@valueChanged](#valuechanged-event) event.

### valueChanged event
When data in the form is changed, the form fires a `valueChanged` event.
This event exposes two arguments, `formData` and `isFormValid`.

`formData` is a key value object, where the field ID is the key, and the data filled in by the user is the value.
`isFormValid` is a boolean telling you if there are __any__ invalid fields in the form.

An example handler is shown below

```js
methods: {
  onValueChanged (formData) {
    console.log(formData) // all the data currently in the form
  }
}
```

##### addOptionRequest event
To allow the use to add new options to a select list the `handleAddOptionRequest` should be a function with the following properties:
 * `completedFunction` a callback function that should be called passing the `option` to be added.
 * `event` the original event triggering the request.
 * `data` object with form field state data.

 The `option` object passed to the `completedFunction` should at least have the following fields
 * `id` unique identifier
 * `label` the label shown to the user
 * `value` the form value

### Options
The FormComponent object can be configured via an options property. 
If no options object is supplied the defaults are used.

| Option name   | Default | Description |
|---------------|---------|-------------|
| showEyeButton | True    | Toggle the visibility of the Eye button


## Form specifications
Example on how to create fields and data objects for using the forms.

```js
const fields = [
  {
    type: 'text',
    id: 'example-text-field',
    label: 'Example text field',
    description: 'This field shows how to create a form field from scratch',
    required: (formData) => true,
    disabled: false,
    readOnly: false,
    visible: (formData) => true,
    validate: (formData) => true
  }
]
  
const data = {
  'example-text-field': 'example value'
}
```

### Type support
We support most HTML input types like number, text, and email. Below is a list of supported types.

| type | renders |
|------|-------------|
| radios | A list of radio buttons |
| single-select | A Vue Multiselect dropdown which supports asynchronous and synchronous option lists
| number | A HTML5 number input |
| text-area | A textarea HTML element |
| date | A Vue Flatpickr Date component |
| date-time | A Vue Flatpickr Date component with 'enableTime = true' |
| checkboxes | A list of checkboxes |
| text | A HTML5 text input |
| url | A HTML5 text url |
| email | A HTML5 text email |
| password | A HTML5 password input |
| file | A HTML5 file input |
| field-group | A type that is used to nest other inputs |

### option field example

Fields that render lists of options like radio buttons, checkboxes, and select dropdowns have an additional `option` parameter.
This option parameter should always contain a function returning a promise. 

This makes it usable for both synchronous and asynchronous rendering of option lists.

```js
const fields = [
  {
    type: 'field-group',
    id: 'example-field-group',
    label: 'Example field group',
    description: 'This field shows how to create a group of fields',
    required: (formData) => true,
    disabled: false,
    readOnly: false,
    visible: (formData) => true,
    validate: (formData) => true,
    options: () => {
      const options = [
        {
          id: '1',
          label: 'Option 1',
          value: '1'
        },
        {
          id: '2',
          label: 'Option 2',
          value: '2'
        }
      ]
      
      return Promise.resolve(options) 
    }
  }
]
```

### field group example
A field group can be used to group a set of fields.
You can specify a list of fields under a field-group via the `children` parameter.

```js
const fields = [
  {
    type: 'field-group',
    id: 'example-field-group',
    label: 'Example field group',
    description: 'This field shows how to create a group of fields',
    required: (formData) => true,
    disabled: false,
    readOnly: false,
    visible: (formData) => true,
    validate: (formData) => true,
    children: [
      {
        type: 'text',
        id: 'example-field-group',
        label: 'Example text field',
        description: 'This field shows how to create a form field from scratch',
        required: (formData) => true,
        disabled: false,
        readOnly: false,
        visible: (formData) => true,
        validate: (formData) => true
      }
    ]
  }
]
  
// Note that the field group itself does not have data 
const data = {
  'example-text-field': 'example value'
}
```

### Required, visible, and validate
As you might have noticed in the above examples, required, visible, and validate are functions returning a boolean.
The reason for this is that you might want to validate a field based on the input of another field. 

Or show / hide a field once another field has a certain value.

```js
const fields = [
  {
    type: 'text',
    id: 'example-text-field',
    label: 'Example text field',
    description: 'This field controls the visibility of example-number-field',
    required: (formData) => true,
    disabled: false,
    readOnly: false,
    visible: (formData) => true,
    validate: (formData) => true
  },
  {
    type: 'number',
    id: 'example-number-field',
    label: 'Example number field',
    description: 'This field is shown if "example-text-field" contains the text "show"',
    required: (formData) => true,
    disabled: false,
    readOnly: false,
    visible: (formData) => {
      return formData['example-text-field'] === 'show'
    },
    validate: (formData) => true
  }
]
```

## Development
The general guidelines and setup of the development environment are described here.

### Build setup

```bash
# install dependencies
yarn install

# serve with hot reload at localhost:8080
yarn run dev

# build for production with minification
yarn run build

# build for production and view the bundle analyzer report
yarn run build --report

# run unit tests
yarn run unit

# run e2e tests
yarn run e2e

# run all tests
yarn test
```

### How to publish
```bash

# Login to NPM with your credentials
npm login

# Run the NPM publish command to the correct scope
npm publish --scope=@molgenis/molgenis-ui-form --access=public

```

For a detailed explanation on how things work, check out the [guide](http://vuejs-templates.github.io/webpack/) and [docs for vue-loader](http://vuejs.github.io/vue-loader).

### Tests
To develop tests please acknowledge the following guidelines.

#### End-to-End test

Please make sure you add the name of the specific test in the test. This is needed to see the test-name in [Saucelabs](https://www.saucelabs.com).
**Example**

```javascript
browser.options.desiredCapabilities.name = 'Example testname'
```
