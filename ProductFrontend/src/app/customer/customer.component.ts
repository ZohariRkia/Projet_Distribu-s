import { Component, OnInit } from '@angular/core';
import {CustomerService} from '../services/customer.service';

@Component({
  selector: 'app-customer',
  templateUrl: './customer.component.html',
  styleUrls: ['./customer.component.css']
})
export class CustomerComponent implements OnInit {
  public customer:any;
  public size:number=5
  public currentPage:number=0
  public totalPages:number
  public pages:Array<number>
  private currentKeyword:string="";
  constructor(private customerService:CustomerService) { }

  ngOnInit(): void {
    this.onGetCustomers();
  }
  onGetCustomers() {
    this.customerService.getCustomers(this.currentPage,this.size)
      .subscribe(data=>{
        this.totalPages=data['page'].totalPages
        this.pages=new Array<number>(this.totalPages)
        this.customer=data
      },error => {
        console.log(error);
      })
  }

  onPageCustomers(i: number) {
    this.currentPage=i
    this.chercherCustomers()
  }
  onChercher(form:any) {
    this.currentPage=0
    this.currentKeyword=form.keyword
    this.chercherCustomers()
  }
  chercherCustomers() {
    this.customerService.getCustomerByKeyword(this.currentKeyword,this.currentPage,this.size)
      .subscribe(data=>{
        this.totalPages=data['page'].totalPages
        this.pages=new Array<number>(this.totalPages)
        this.customer=data
      },error => {
        console.log(error);
      })
  }

  onDeleteCustomer(p) {
    let conf=confirm("Eres vous sûre ?")
    if(conf) {
      this.customerService.deleteRessource(p._links.self.href)
        .subscribe(data=>{
          this.chercherCustomers()
        },error => {
          console.log(error)
        })
    }
  }

  onSaveProduct(data: any) {

    this.customerService.saveResource(this.customerService.host+"/customers",data)
      .subscribe(resp=>{
        console.log(resp)
      },error => {
        console.log(error)
      })
  }

}
