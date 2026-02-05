import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Agregar } from './agregar/agregar';
import { Buscar } from './buscar/buscar';
import { Listar } from './listar/listar';

const routes: Routes = [

  {path: 'agregar', component:Agregar},
  {path: 'buscar', component:Buscar},
  {path: 'listar', component:Listar},
  

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
