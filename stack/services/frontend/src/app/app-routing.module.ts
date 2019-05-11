import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {DashboardComponent} from './dashboard/dashboard.component';
import {UploadComponent} from "./upload/upload.component";
import {RandomComponent} from "./random/random.component";
import {DeleteComponent} from "./delete/delete.component";
import {OrchestrateComponent} from "./orchestrate/orchestrate.component";
import {DisplayComponent} from "./display/display.component";
import {AlbumComponent} from "./album/album.component";

const routes: Routes = [
  {path: '', redirectTo: '/dashboard', pathMatch: 'full'},
  {path: 'dashboard', component: DashboardComponent},
  {path: 'upload', component: UploadComponent},
  {path: 'display', component: DisplayComponent},
  {path: 'random', component: RandomComponent},
  {path: 'delete', component: DeleteComponent},
  {path: 'orchestrate', component: OrchestrateComponent},
  {path: 'album', component: AlbumComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
