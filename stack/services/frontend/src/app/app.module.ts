import {BrowserModule} from '@angular/platform-browser';
import {ComponentFactory, NgModule} from '@angular/core';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule} from '@angular/forms';

import {AppComponent} from './app.component';
import {UploadComponent} from './upload/upload.component';
import {DisplayComponent} from './display/display.component';
import {RandomComponent} from './random/random.component';
import {DeleteComponent} from './delete/delete.component';
import {OrchestrateComponent} from './orchestrate/orchestrate.component';
import {AppRoutingModule} from './app-routing.module';
import {DashboardComponent} from './dashboard/dashboard.component';
import {AlbumComponent} from './album/album.component';
import {PreviewComponent} from "./album/preview/preview.component";
import {PreviewService} from "./album/preview/preview.service";

@NgModule({
  declarations: [
    AppComponent,
    UploadComponent,
    DisplayComponent,
    RandomComponent,
    DeleteComponent,
    OrchestrateComponent,
    DashboardComponent,
    AlbumComponent,
    PreviewComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [PreviewService],
  bootstrap: [AppComponent],
  entryComponents: [PreviewComponent]
})
export class AppModule {
}
