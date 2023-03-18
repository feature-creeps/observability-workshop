import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { UploadComponent, UploadService } from './features/upload';
import { DisplayComponent, DisplayService } from './features/display';
import { RandomComponent, RandomService } from './features/random';
import { DeleteComponent, DeleteService } from './features/delete';
import { OrchestrateComponent, OrchestrateService } from './features/orchestrate';
import { AppRoutingModule } from './app-routing.module';
import { DashboardComponent } from './features/dashboard';
import { AlbumComponent, AlbumService, PreviewComponent, PreviewService } from './features/album';
import { ToolsComponent } from './features/tools';
import { TrafficGeneratorComponent, TrafficgenService } from "./features/trafficgen";

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
        PreviewComponent,
        ToolsComponent,
        TrafficGeneratorComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        FormsModule
    ],
    providers: [
        AlbumService,
        DeleteService,
        DisplayService,
        OrchestrateService,
        RandomService,
        TrafficgenService,
        UploadService,
        PreviewService
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
