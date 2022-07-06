import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ProduitComponent} from './produit/produit.component';
import {CustomerComponent} from './customer/customer.component';
import {BillComponent} from './bill/bill.component';

const routes: Routes = [
  {
    path:"products",
    component:ProduitComponent
  },
  {
    path:"customers",
    component:CustomerComponent
  },
  {
    path:"billings",
    component:BillComponent
  },
  {
    path:"",
    redirectTo:"/products",
    pathMatch:"full"
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

