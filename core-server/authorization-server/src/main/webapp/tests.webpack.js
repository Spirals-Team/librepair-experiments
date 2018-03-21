// Require spec tests
var spec = require.context('./__tests__', true, /\.spec\.js$/);
spec.keys().forEach(spec);

// Require helpers
var testHelpers = require.context('./__tests__/helpers');
testHelpers.keys().forEach(testHelpers);