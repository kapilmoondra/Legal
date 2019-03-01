import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CustomRequestRoutingModule } from './custom-request-routing.module';
import { RecourseComponent } from './recourse/recourse.component';
import { CustomRequestService } from './custom-request.service';
import { StageComponent } from './stage/stage.component';
import { CourtComponent } from './court/court.component';
import { DataTableModule } from '../../shared/components/data-table/data-table.module';

@NgModule({
  imports: [
    CommonModule,
    CustomRequestRoutingModule,
    DataTableModule
  ],
  declarations: [RecourseComponent, StageComponent, CourtComponent],
  providers:[CustomRequestService]
})
export class CustomRequestModule { }
