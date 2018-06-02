import { DragDropService } from './../service/drag-drop/drag-drop.service';
import { WorkflowUtilService } from './../service/workflow-graph/util/workflow-util.service';
import { WorkflowActionService } from './../service/workflow-graph/model/workflow-action.service';
import { Component, OnInit } from '@angular/core';

import { OperatorMetadataService } from '../service/operator-metadata/operator-metadata.service';
import { JointUIService } from '../service/joint-ui/joint-ui.service';
import { StubOperatorMetadataService } from '../service/operator-metadata/stub-operator-metadata.service';


@Component({
  selector: 'texera-workspace',
  templateUrl: './workspace.component.html',
  styleUrls: ['./workspace.component.scss'],
  providers: [
    // StubOperatorMetadataService can be used for debugging without start the backend server
    { provide: OperatorMetadataService, useClass: StubOperatorMetadataService },
    // OperatorMetadataService,

    JointUIService,
    WorkflowActionService,
    WorkflowUtilService,
    DragDropService
  ]
})
export class WorkspaceComponent implements OnInit {

  constructor(
  ) { }

  ngOnInit() {
  }

}
