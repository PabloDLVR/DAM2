import { NgModule, provideBrowserGlobalErrorListeners } from '@angular/core';
import { BrowserModule, provideClientHydration, withEventReplay } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing-module';
import { App } from './app';
import { Buscar } from './buscar/buscar';
import { Listar } from './listar/listar';
import { Agregar } from './agregar/agregar';
import { ImagenesPipe } from './pipes/imagenes-pipe';

@NgModule({
  declarations: [
    App,
    Buscar,
    Listar,
    Agregar,
    ImagenesPipe
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule
  ],
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideClientHydration(withEventReplay()),
  ],
  bootstrap: [App]
})
export class AppModule { }
