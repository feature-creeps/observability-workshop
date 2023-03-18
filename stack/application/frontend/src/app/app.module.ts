import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { UploadComponent } from './features/upload';
import { DisplayComponent } from './features/display';
import { RandomComponent } from './features/random';
import { DeleteComponent } from './features/delete';
import { OrchestrateComponent } from './features/orchestrate';
import { AppRoutingModule } from './app-routing.module';
import { DashboardComponent } from './features/dashboard';
import { AlbumComponent, PreviewComponent, PreviewService } from './features/album';
import { ToolsComponent } from './features/tools';
import { TrafficGeneratorComponent } from "./features/trafficgen";

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
    providers: [PreviewService],
    bootstrap: [AppComponent]
})
export class AppModule {
}
