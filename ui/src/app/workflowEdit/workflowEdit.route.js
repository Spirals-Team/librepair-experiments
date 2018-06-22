import workflowEditTemplate from './workflowEdit.tpl.html';

/*@ngInject*/
export default function WorkflowRoutes($stateProvider) {
  $stateProvider
      .state('home.workflowEdit', {
          url: 'workflow',
          module: 'private',
          //auth: ['SYS_ADMIN', 'TENANT_ADMIN', 'CUSTOMER_USER'],
          views: {
              "content@home": {
                  controller: 'WorkflowEditController',
                  controllerAs: 'vm',
                  templateUrl: workflowEditTemplate
              }
          },
          data: {
              pageTitle: 'home.workflow'
          },
          ncyBreadcrumb: {
            label: '{"icon": "devices_other", "label": "workflow.workflow"}'
          }
      });
}
