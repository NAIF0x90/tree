import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class StatementService {

  constructor(private http: HttpClient, 
    private router: Router) {

  }

  getStatemnts(accountId: string, amount: string, amountTo: string, dateFrom: string, dateTo: string) {
    let p = new HttpParams();
    console.log(p + '/////////////')

    if(amount){
        p = p.append('amount', amount);
    }
    if(amountTo){
        p = p.append('amountTo', amountTo);
    }
    if(dateTo){
        p = p.append('dateFieldTo', dateTo);
    }
    if(dateFrom){
        p = p.append('dateFieldFrom', dateFrom);
    }

    let username = sessionStorage.getItem('username');
    let password = sessionStorage.getItem('password');

    if(username == null ){
        username = '';
        this.router.navigate(['/login']);
    }
    if(password == null ){
        password = '';
        this.router.navigate(['/login']);
    }
    
    return this.http.get(`http://localhost:8080/api/statement/getStatementsByAccountId/` + accountId,  {headers: { authorization: this.createBasicAuthToken(username, password) }, params: p});
  }


  createBasicAuthToken(username: String, password: String) {
    return 'Basic ' + window.btoa(username + ":" + password)
  }
}