import { ChangeDetectorRef, Component } from '@angular/core';
import { Router } from '@angular/router';
import { StatementService } from '../services/StatementsService';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {


  accountId: string;
  amount: string;
  amountTo: string;
  dateFrom: string;
  dateTo: string;
  statements: any;

  constructor( private router: Router,
    private statementsService: StatementService,
    private changeDetection: ChangeDetectorRef
    ){

  }

  getStatements(){

    if(!this.accountId){
      alert("please enter account id");
      return;
    }

      this.statementsService.getStatemnts(this.accountId, this.amount, this.amountTo, this.dateFrom, this.dateTo).subscribe({
        next: (v) => {
          this.statements = v;
          this.changeDetection.detectChanges();
        },
        error: (e) => {
          if(e.status == 401){
            this.router.navigate(['/login']);
          }

          if(e.status == 400){
            alert(e.error);
          }
          
        }
    })
  }

}
