import { moduleFor, test } from 'ember-qunit';

moduleFor('route:manage/alerts', 'Unit | Route | manage/alerts', {
  // Specify the other units that are required for this test.
  needs: ['service:session']
});

test('it exists', function(assert) {
  let route = this.subject();
  assert.ok(route);
});
