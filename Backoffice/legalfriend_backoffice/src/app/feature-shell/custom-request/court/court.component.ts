import { Component, OnInit, ViewChild } from '@angular/core';
import { CustomRequestService } from '../custom-request.service';
import { ActionColumnModel } from '../../../shared/models/data-table/action-column.model';
import { DataTableComponent } from '../../../shared/components/data-table/data-table.component';
import { DatePipe } from '../../../../../node_modules/@angular/common';
import { courtConfig } from './courtconfig';
declare var $;

@Component({
  selector: 'app-court',
  templateUrl: './court.component.html',
  styleUrls: ['./court.component.css']
})
export class CourtComponent implements OnInit {
  tableInputData = [];
  actionColumnConfig: ActionColumnModel;
  columns = courtConfig;
  rowSelect = false;
  hoverTableRow = true;
  @ViewChild(DataTableComponent) dataTableComponent: DataTableComponent;
  constructor(private _customRequestService: CustomRequestService, private _datePipe: DatePipe) { }

  ngOnInit() {
    this.setActionConfig();
    this.GetAllCourts();
  }

  setActionConfig() {
    this.actionColumnConfig = new ActionColumnModel();
    this.actionColumnConfig.displayName = 'Action';
    this.actionColumnConfig.showCancel = true;
    this.actionColumnConfig.showOk = true;
  }

  GetAllCourts() {
    this.tableInputData = [];
    this._customRequestService.GetAllCourts().subscribe(
      (result) => {
        if (result) {
          result.forEach(d => {
            this.tableInputData.push({
              id: d.id,
              description: d.courtDesc,
              name: d.courtName,
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
      this._customRequestService.ApprovedCourt(data.data.id).subscribe(
        (result) => {
          if (result.status === 200) {
            $.toaster({ priority: 'success', title: 'Success', message: 'Update Successfully.' });
          } else {
            $.toaster({ priority: 'error', title: 'Error', message: 'Updated Failed.' });
          }
          this.GetAllCourts();
        },
        err => {
          console.log(err);
        }
      );
    } else if (data.eventType === 'cancel') {
      this._customRequestService.DeclineCourt(data.data.id).subscribe(
        (result) => {
          if (result.status === 200) {
            $.toaster({ priority: 'success', title: 'Success', message: 'Decline Successfully.' });
          } else {
            $.toaster({ priority: 'error', title: 'Error', message: 'Decline Failed.' });
          }
          this.GetAllCourts();
        },
        err => {
          console.log(err);
        }
      );
    }
  }

}
