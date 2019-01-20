import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import {AddDependentDialog, AddEmployeeDialog, AppComponent} from './app.component';
import {HttpClientModule} from "@angular/common/http";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {
  MatButtonModule,
  MatCardModule,
  MatDialogModule, MatFormFieldModule,
  MatIconModule, MatInputModule,
  MatListModule, MatOptionModule, MatSelectModule,
  MatSidenavModule, MatSliderModule, MatToolbarModule
} from "@angular/material";
import {FormsModule} from "@angular/forms";
import {FlexLayoutModule} from "@angular/flex-layout";

@NgModule({
  declarations: [
    AppComponent,
    AddEmployeeDialog,
    AddDependentDialog
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatListModule,
    MatCardModule,
    MatSidenavModule,
    MatIconModule,
    MatDialogModule,
    MatFormFieldModule,
    FormsModule,
    MatInputModule,
    MatToolbarModule,
    FlexLayoutModule,
    MatOptionModule,
    MatSelectModule,
    MatSliderModule
  ],
  providers: [],
  entryComponents: [AddEmployeeDialog, AddDependentDialog],
  bootstrap: [AppComponent]
})
export class AppModule { }
