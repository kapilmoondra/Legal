import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RecourseComponent } from './recourse/recourse.component';
import { StageComponent } from './stage/stage.component';
import { CourtComponent } from './court/court.component';

const routes: Routes = [
  {
    path: 'recourse', component: RecourseComponent
  },
  {
    path: 'stage', component: StageComponent
  },
  {
    path: 'court', component: CourtComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CustomRequestRoutingModule { }
