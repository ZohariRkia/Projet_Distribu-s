import { Component, OnInit } from '@angular/core';
import {BillService} from '../services/bill.service';

@Component({
  selector: 'app-bill',
  templateUrl: './bill.component.html',
  styleUrls: ['./bill.component.css']
})
export class BillComponent implements OnInit {

  public facture:any;
  public size:number=5
  public currentPage:number=0
  public totalPages:number
  public pages:Array<number>
  private currentKeyword:string="";
  constructor(private factureService:BillService) { }

  ngOnInit(): void {
    this.onGetBills();
  }
  onPageBill(i: number) {
    this.currentPage=i
    this.onGetBills()
  }
  onGetBills() {
    this.factureService.getBillings(this.currentPage,this.size)
      .subscribe(data=>{
        this.totalPages=data['numberOfElements']
        this.pages=new Array<number>(this.totalPages)
        this.facture=data
      },error => {
        console.log(error);
      })
  }
}
