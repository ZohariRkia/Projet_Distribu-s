import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class BillService {
  public host:string='http://localhost:8885/BILLING-SERVICE'

  constructor(private httpClient: HttpClient) { }

  public getBillings(page:number,size:number){
    return this.httpClient.get(this.host+'/fullBill?page='+page+'&size='+size);
  }

}
