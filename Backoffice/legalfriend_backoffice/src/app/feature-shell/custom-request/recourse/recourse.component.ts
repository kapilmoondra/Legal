import { Component, OnInit, ViewChild } from '@angular/core';
import { CustomRequestService } from '../custom-request.service';
import { ActionColumnModel } from '../../../shared/models/data-table/action-column.model';
import { recourseConfig } from './recourseconfig';
import { DataTableComponent } from '../../../shared/components/data-table/data-table.component';
import { DatePipe } from '../../../../../node_modules/@angular/common';
declare var $;
@Component({
  selector: 'app-recourse',
  templateUrl: './recourse.component.html',
  styleUrls: ['./recourse.component.css']
})
export class RecourseComponent implements OnInit {
  tableInputData = [];
  actionColumnConfig: ActionColumnModel;
  columns = recourseConfig;
  rowSelect = false;
  hoverTableRow = true;
  @ViewChild(DataTableComponent) dataTableComponent: DataTableComponent;
  constructor(private _customRequestService: CustomRequestService, private _datePipe: DatePipe) { }

  ngOnInit() {
    this.setActionConfig();
    this.GetAllRecourse();
  }

  setActionConfig() {
    this.actionColumnConfig = new ActionColumnModel();
    this.actionColumnConfig.displayName = 'Action';
    this.actionColumnConfig.showCancel = true;
    this.actionColumnConfig.showOk = true;
  }

  GetAllRecourse() {
    this.tableInputData = [];
    this._customRequestService.GetAllRecourses().subscribe(
      (result) => {
        if (result) {
          result.forEach(d => {
            this.tableInputData.push({
              id: d.id,
              code: d.recourseCode,
              description: d.recourseDesc,
              name: d.recourseName,
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
      this._customRequestService.ApprovedRecourse(data.data.id).subscribe(
        (result) => {
          if (result.status === 200) {
            $.toaster({ priority: 'success', title: 'Success', message: 'Update Successfully.' });
          } else {
            $.toaster({ priority: 'error', title: 'Error', message: 'Updated Failed.' });
          }
          this.GetAllRecourse();
        },
        err => {
          console.log(err);
        }
      );
    } else if (data.eventType === 'cancel') {
      this._customRequestService.DeclineRecourse(data.data.id).subscribe(
        (result) => {
          if (result.status === 200) {
            $.toaster({ priority: 'success', title: 'Success', message: 'Decline Successfully.' });
          } else {
            $.toaster({ priority: 'error', title: 'Error', message: 'Decline Failed.' });
          }
          this.GetAllRecourse();
        },
        err => {
          console.log(err);
        }
      );
    }
  }

}
