import {Component, Inject, ViewChild} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef, MatListOption, MatSelectionList} from "@angular/material";
import {Employee} from "./entities/Employee";
import {EmployeeService} from "./Services/EmployeeService";
import {SelectionModel} from "@angular/cdk/collections";
import {Dependent, DependentType} from "./entities/Dependent";
import {PayPeriodLength, SalarySummary} from "./entities/SalarySummary";
import {ExpensesService} from "./Services/ExpensesService";
import {PaycheckService} from "./Services/PaycheckService";
import {mergeMap} from "rxjs/operators";

export interface AddEmployeeDialogData {
  firstName: string;
  lastName: string;
}

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Employee Benefits Cost Calculator';
  dependentListDefaultTitle = "No Employee Selected";

  employeeList: Employee[] = [];

  selectedEmployee: Employee;
  selectedEmployeeSalarySummary: SalarySummary = new SalarySummary();
  salarySummary = new SalarySummary();
  paychecksPerYear : number;
  amountPerPaycheck: number;
  newPaychecksPerYear : number;
  newAmountPerPaycheck: number;

  @ViewChild('employeeSelectionList')
  employeeSelectionList: MatSelectionList;

  @ViewChild('dependentDisplayList')
  dependentDisplayList: MatSelectionList;

  constructor(private employeeService: EmployeeService,
              private expensesService: ExpensesService,
              private paycheckService: PaycheckService,
              public dialog: MatDialog) {
    employeeService.getAllEmployees().subscribe(employees => {
      this.employeeList = employees;
      this.updatePaycheckInfo();
    });
  }

  ngOnInit() {
    this.employeeSelectionList.selectedOptions = new SelectionModel<MatListOption>(false)
  }

  updateExpenseInfo() {
    this.expensesService.getSalarySummary().subscribe(salarySummary =>
      this.salarySummary = salarySummary
    );
    if(this.selectedEmployee) {
      this.employeeService.getEmployeeSalarySummary(this.selectedEmployee.id).subscribe(sSummary =>
        this.selectedEmployeeSalarySummary = sSummary
      )
    }
  }

  updatePaycheckInfo() {
    this.paycheckService.getAmountPerPaycheck().pipe(
      mergeMap(amount => {
        this.amountPerPaycheck = amount;
        this.newAmountPerPaycheck = amount;
        return this.paycheckService.getNumYearlyPaychecks();
      })
    ).subscribe(numYearly => {
      this.paychecksPerYear = numYearly;
      this.newPaychecksPerYear = numYearly;
      this.updateExpenseInfo();
    });
  }

  selectEmployee(employee: Employee) {
    this.selectedEmployee = employee;
    this.updateExpenseInfo();
  }

  addEmployee(employee: Employee) {
    this.employeeService.addEmployee(employee).subscribe(employee => {
      this.employeeList.push(employee);
      this.updateExpenseInfo();
    })
  }

  deleteEmployee(employee: Employee) {
    this.employeeService.deleteEmployee(employee.id).subscribe(() => {
      console.log("Deleting Employee: " + employee);
      this.employeeList = this.employeeList.filter(e => e.id != employee.id);
      if(this.selectedEmployee == employee) {
        this.selectedEmployee = undefined;
      }
      this.updateExpenseInfo();
    })
  }

  addDependent(dependent: Dependent) {
    if(this.selectedEmployee) {
      this.employeeService.addDependent(this.selectedEmployee.id, dependent).subscribe(dependent => {
        this.selectedEmployee.dependents.push(dependent);
        this.updateExpenseInfo();
      })
    }
  }

  deleteDependent(dependent: Dependent) {
    if(this.selectedEmployee) {
      this.employeeService.deleteDependent(this.selectedEmployee.id, dependent.id).subscribe(() => {
        this.selectedEmployee.dependents = this.selectedEmployee.dependents.filter(d => d.id != dependent.id);
        this.updateExpenseInfo();
      })
    }
  }

  getDependentIcon(dependent: Dependent) {
    let depTypeString = dependent.dependentType as unknown as string;
    switch(DependentType[depTypeString]){
      case DependentType.SPOUSE:
        return "supervisor_account";
      case DependentType.CHILD:
        return "face";
      default:
        return "account_circle"
    }
  }

  getPayPeriodLengths() {
    return Object.keys(PayPeriodLength).filter(key => isNaN(Number(key)));
  }

  submitPaycheckChanges() {
    this.paycheckService.setAmountPerPaycheck(this.newAmountPerPaycheck).pipe(
      mergeMap(amount => {
        this.amountPerPaycheck = amount;
        return this.paycheckService.setNumYearlyPaychecks(this.newPaychecksPerYear);
      })
    ).subscribe(numYearly => {
      this.paychecksPerYear = numYearly;
      this.updateExpenseInfo();
    });
  }

  addEmployeeDialog() {
    const dialogRef = this.dialog.open(AddEmployeeDialog, {
      width: '250px',
      data: {}
    });
    dialogRef.afterClosed().subscribe(result => {
      if(result) {
        this.addEmployee({
          id: -1,
          firstName: result.firstName,
          lastName: result.lastName,
          dependents: []
        })
      }
    })
  };

  addDependentDialog() {
    const dialogRef = this.dialog.open(AddDependentDialog, {
      width: '250px',
      data: { dependentTypes: Object.keys(DependentType).filter(key => isNaN(Number(key)))}
    });
    dialogRef.afterClosed().subscribe(result => {
        if (result) {
          let depType = <string>result.dependentType; //Fixes issue with TS compiler being confused about dependent type (https://stackoverflow.com/questions/17380845/how-to-convert-string-to-enum-in-typescript)
          this.addDependent({
            id: -1,
            firstName: result.firstName,
            lastName: result.lastName,
            dependentType: DependentType[depType]
          })
        }
      }
    )

  }
}

@Component({
  selector: 'add-employee-dialog',
  templateUrl: 'add-employee-dialog.html',
})
export class AddEmployeeDialog {

  constructor(
    public dialogRef: MatDialogRef<AddEmployeeDialog>,
    @Inject(MAT_DIALOG_DATA) public data: AddEmployeeDialogData) {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}

@Component({
  selector: 'add-dependent-dialog',
  templateUrl: 'add-dependent-dialog.html',
})
export class AddDependentDialog {

  constructor(
    public dialogRef: MatDialogRef<AddEmployeeDialog>,
    @Inject(MAT_DIALOG_DATA) public data: any) {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}

