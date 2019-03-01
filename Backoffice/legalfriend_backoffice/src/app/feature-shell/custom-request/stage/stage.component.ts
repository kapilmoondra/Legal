import { Component, OnInit, ViewChild } from '@angular/core';
import { ActionColumnModel } from '../../../shared/models/data-table/action-column.model';
import { stageConfig } from './stageConfig';
import { DataTableComponent } from '../../../shared/components/data-table/data-table.component';
import { CustomRequestService } from '../custom-request.service';
import { DatePipe } from '../../../../../node_modules/@angular/common';
declare var $;
@Component({
  selector: 'app-stage',
  templateUrl: './stage.component.html',
  styleUrls: ['./stage.component.css']
})
export class StageComponent implements OnInit {
  tableInputData = [];
  actionColumnConfig: ActionColumnModel;
  columns = stageConfig;
  rowSelect = false;
  hoverTableRow = true;
  @ViewChild(DataTableComponent) dataTableComponent: DataTableComponent;
  constructor(private _customRequestService: CustomRequestService, private _datePipe: DatePipe) { }

  ngOnInit() {
    this.setActionConfig();
    this.GetAllStages();
  }

  setActionConfig() {
    this.actionColumnConfig = new ActionColumnModel();
    this.actionColumnConfig.displayName = 'Action';
    this.actionColumnConfig.showCancel = true;
    this.actionColumnConfig.showOk = true;
  }

  GetAllStages() {
    this.tableInputData = [];
    this._customRequestService.GetAllStages().subscribe(
      (result) => {
        if (result) {
          result.forEach(d => {
            this.tableInputData.push({
              id: d.id,
              recourseName: d.recourse.recourseName,
              code: d.stageCode,
              description: d.stageDesc,
              name: d.stageName,
              user: d.user.firstName + ' ' + d.user.lastName,
              date: this._datePipe.transform(d.createdDate, 'dd MMM yyyy'),
              status: d.status
            });
          });
          this.dataTableComponent.ngOnInit();
        }
      },
      err => {
        console.log(err);
      }
    );
  }

  onRowClick(data) {

  }
  onRowDoubleClick(data) {

  }

  onRowSelect(data) {

  }

  onActionBtnClick(data) {
    if (data.eventType === 'ok') {
      this._customRequestService.ApprovedStage(data.data.id).subscribe(
        (result) => {
          if (result.status === 200) {
            $.toaster({ priority: 'success', title: 'Success', message: 'Update Successfully.' });
          } else {
            $.toaster({ priority: 'error', title: 'Error', message: 'Updated Failed.' });
          }
          this.GetAllStages();
        },
        err => {
          console.log(err);
        }
      );
    } else if (data.eventType === 'cancel') {
      this._customRequestService.DeclineStage(data.data.id).subscribe(
        (result) => {
          if (result.status === 200) {
            $.toaster({ priority: 'success', title: 'Success', message: 'Decline Successfully.' });
          } else {
            $.toaster({ priority: 'error', title: 'Error', message: 'Decline Failed.' });
          }
          this.GetAllStages();
        },
        err => {
          console.log(err);
        }
      );
    }
  }

}
