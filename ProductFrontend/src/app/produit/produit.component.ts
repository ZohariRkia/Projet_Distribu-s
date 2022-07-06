import { Component, OnInit } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ProduitService} from '../services/produit.service';

@Component({
  selector: 'app-produit',
  templateUrl: './produit.component.html',
  styleUrls: ['./produit.component.css']
})
export class ProduitComponent implements OnInit {
  public produit:any;
  public size:number=5
  public currentPage:number=0
  public totalPages:number
  public pages:Array<number>
  private currentKeyword:string="";
  constructor(private prodService:ProduitService) { }

  ngOnInit(): void {
    this.onGetProducts();
  }
  onGetProducts() {
    this.prodService.getProduct(this.currentPage,this.size)
      .subscribe(data=>{
        this.totalPages=data['page'].totalPages
        this.pages=new Array<number>(this.totalPages)
        this.produit=data
      },error => {
        console.log(error);
      })
  }

  onPageProduct(i: number) {
    this.currentPage=i
    this.chercherProduits()
  }
  onChercher(form:any) {
    this.currentPage=0
    this.currentKeyword=form.keyword
    this.chercherProduits()
  }
  chercherProduits() {
    this.prodService.getProductByKeyword(this.currentKeyword,this.currentPage,this.size)
      .subscribe(data=>{
        this.totalPages=data['page'].totalPages
        this.pages=new Array<number>(this.totalPages)
        this.produit=data
      },error => {
        console.log(error);
      })
  }

  onDeleteProduct(p) {
     let conf=confirm("Eres vous sûre ?")
    if(conf) {
      this.prodService.deleteRessource(p._links.self.href)
        .subscribe(data=>{
          this.chercherProduits()
        },error => {
          console.log(error)
        })
    }
  }

  onSaveProduct(data: any) {

    this.prodService.saveResource(this.prodService.host+"/products",data)
      .subscribe(resp=>{
        console.log(resp)
      },error => {
        console.log(error)
      })
  }
}
