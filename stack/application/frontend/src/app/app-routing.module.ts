import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { DashboardComponent } from './features/dashboard';
import { UploadComponent } from './features/upload';
import { RandomComponent } from './features/random';
import { DeleteComponent } from './features/delete';
import { OrchestrateComponent } from './features/orchestrate';
import { DisplayComponent } from './features/display';
import { AlbumComponent } from './features/album';
import { ToolsComponent } from './features/tools';
import { TrafficGeneratorComponent } from './features/trafficgen';

const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'upload', component: UploadComponent },
  { path: 'display', component: DisplayComponent },
  { path: 'random', component: RandomComponent },
  { path: 'delete', component: DeleteComponent },
  { path: 'orchestrate', component: OrchestrateComponent },
  { path: 'album', component: AlbumComponent },
  { path: 'tools', component: ToolsComponent },
  { path: 'trafficgen', component: TrafficGeneratorComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { onSameUrlNavigation: 'reload' })],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
