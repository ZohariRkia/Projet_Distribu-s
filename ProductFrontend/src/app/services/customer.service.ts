import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  constructor(private httpClient: HttpClient) { }
  public host:string='http://localhost:8885/CUSTOMER-SERVICE'

  public getCustomers(page:number,size:number){
    return this.httpClient.get(this.host+'/customers?page='+page+'&size='+size);
  }

  public getCustomerByKeyword(mc:string,page:number,size:number){
    return this.httpClient.get(this.host+'/customers/search/byNamePage?mc='+mc+'&page='+page+'&size='+size);
  }

  public deleteRessource(url){
    return this.httpClient.delete(url)
  }

  public saveResource(url,data){
    return this.httpClient.post(url,data)
  }
}
