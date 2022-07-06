import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ProduitService {
  public host:string='http://localhost:8885/PRODUCT-SERVICE'

  constructor(private httpClient: HttpClient) { }

  public getProduct(page:number,size:number){
    return this.httpClient.get(this.host+'/products?page='+page+'&size='+size);
  }

  public getProductByKeyword(mc:string,page:number,size:number){
    return this.httpClient.get(this.host+'/products/search/byNamePage?mc='+mc+'&page='+page+'&size='+size);
  }

  public deleteRessource(url){
    return this.httpClient.delete(url)
  }

  public saveResource(url,data){
    return this.httpClient.post(url,data)
  }
}
