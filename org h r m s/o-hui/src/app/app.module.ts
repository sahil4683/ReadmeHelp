import { BrowserModule, Title } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { HomeComponent } from './home/home.component';
import { authInterceptorProviders } from './_helpers/auth.interceptor';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { DataTablesModule } from 'angular-datatables';
import { ToastrModule } from 'ngx-toastr';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ReactiveFormsModule } from '@angular/forms';
import { NgSelectModule } from '@ng-select/ng-select';
import { PdfJsViewerModule } from 'ng2-pdfjs-viewer';
import { ErrorComponent } from './components/error/error.component';

import { RegistrationComponent } from './_pages/webhims/reception/reception_reception/registration/registration.component';
import { AppointmentComponent } from './_pages/webhims/reception/reception_reception/appointment/appointment.component';
import { AdmissionComponent } from './_pages/webhims/reception/reception_reception/admission/admission.component';
import { ScannedImageComponent } from './_pages/webhims/reception/reception_reception/scanned-image/scanned-image.component';

import { OpdComponent } from './_pages/webhims/reception/reception_bill/opd/opd.component';
import { RadioComponent } from './_pages/webhims/reception/reception_bill/radio/radio.component';
import { LabComponent } from './_pages/webhims/reception/reception_bill/lab/lab.component';
import { OpdhealthCheckupComponent } from './_pages/webhims/reception/reception_bill/opdhealth-checkup/opdhealth-checkup.component';
import { CashDuesComponent } from './_pages/webhims/reception/reception_bill/cash-dues/cash-dues.component';

import { OpdRefundComponent } from './_pages/webhims/reception/reception_bill_refund/opd-refund/opd-refund.component';
import { RadioRefundComponent } from './_pages/webhims/reception/reception_bill_refund/radio-refund/radio-refund.component';
import { LabRefundComponent } from './_pages/webhims/reception/reception_bill_refund/lab-refund/lab-refund.component';
import { OpdhealthCheckupRefundComponent } from './_pages/webhims/reception/reception_bill_refund/opdhealth-checkup-refund/opdhealth-checkup-refund.component';

import { BillDetailSheetComponent } from './_pages/webhims/ipd/bill/bill-detail-sheet/bill-detail-sheet.component';
import { ProvisionalBillComponent } from './_pages/webhims/ipd/bill/provisional-bill/provisional-bill.component';
import { FinalIpdBillComponent } from './_pages/webhims/ipd/bill/final-ipd-bill/final-ipd-bill.component';

import { BedShiftingComponent } from './_pages/webhims/ipd/bed/bed-shifting/bed-shifting.component';
import { BedStatusComponent } from './_pages/webhims/ipd/bed/bed-status/bed-status.component';
import { VacantBedComponent } from './_pages/webhims/ipd/bed/vacant-bed/vacant-bed.component';

import { BedDischargeComponent } from './_pages/webhims/ipd/dischange/bed-discharge/bed-discharge.component';
import { DischargeClearanceComponent } from './_pages/webhims/ipd/dischange/discharge-clearance/discharge-clearance.component';

import { ReceiptComponent } from './_pages/webhims/account/entry/receipt/receipt.component';
import { PaymentComponent } from './_pages/webhims/account/entry/payment/payment.component';

import { IpdRegisterComponent } from './_pages/webhims/report/register_report/ipd-register/ipd-register.component';
import { IpdReceiptRegisterComponent } from './_pages/webhims/report/register_report/ipd-receipt-register/ipd-receipt-register.component';
import { OpdRegisterComponent } from './_pages/webhims/report/register_report/opd-register/opd-register.component';
import { LabRegisterComponent } from './_pages/webhims/report/register_report/lab-register/lab-register.component';
import { RadioRegisterComponent } from './_pages/webhims/report/register_report/radio-register/radio-register.component';
import { HealthCheckupRegisterComponent } from './_pages/webhims/report/register_report/health-checkup-register/health-checkup-register.component';
import { CashDueReportComponent } from './_pages/webhims/report/register_report/cash-due-report/cash-due-report.component';
import { OrganizationReportComponent } from './_pages/webhims/report/register_report/organization-report/organization-report.component';

import { MisComponent } from './_pages/webhims/report/analysis_report/mis/mis.component';
import { GroupwiseTestwiseAnalysisComponent } from './_pages/webhims/report/analysis_report/groupwise-testwise-analysis/groupwise-testwise-analysis.component';

import { AdmissionDischargeComponent } from './_pages/webhims/report/routine_report/admission-discharge/admission-discharge.component';
import { FeeCollectionReportComponent } from './_pages/webhims/report/routine_report/fee-collection-report/fee-collection-report.component';

// Master
import { IpdTestComponent } from './_pages/webhims/master/test_master/ipd-test/ipd-test.component';
import { OpdTestComponent } from './_pages/webhims/master/test_master/opd-test/opd-test.component';

import { ConsultantDoctorComponent } from './_pages/webhims/master/doctor/consultant-doctor/consultant-doctor.component';
import { ReferringDoctorComponent } from './_pages/webhims/master/doctor/referring-doctor/referring-doctor.component';

import { OpdBillMasterComponent } from './_pages/webhims/master/bill_master/opd-bill-master/opd-bill-master.component';
import { LabBillMasterComponent } from './_pages/webhims/master/bill_master/lab-bill-master/lab-bill-master.component';
import { RadioBillMasterComponent } from './_pages/webhims/master/bill_master/radio-bill-master/radio-bill-master.component';

import { AppointmentSchedulerComponent } from './_pages/webhims/master/appointment_master/appointment-scheduler/appointment-scheduler.component';

import { IpdPackageMasterComponent } from './_pages/webhims/master/package_master/ipd-package-master/ipd-package-master.component';
import { OpdPackageMasterComponent } from './_pages/webhims/master/package_master/opd-package-master/opd-package-master.component';

import { DepartmentComponent } from './_pages/webhims/master/other_masters1/department/department.component';
import { SubdepartmentComponent } from './_pages/webhims/master/other_masters1/subdepartment/subdepartment.component';
import { ConcessionComponent } from './_pages/webhims/master/other_masters1/concession/concession.component';
import { SuperGroupComponent } from './_pages/webhims/master/other_masters1/super-group/super-group.component';
import { GroupComponent } from './_pages/webhims/master/other_masters1/group/group.component';
import { SubGroupComponent } from './_pages/webhims/master/other_masters1/sub-group/sub-group.component';
import { SubGroup1Component } from './_pages/webhims/master/other_masters1/sub-group1/sub-group1.component';
import { SubGroup2Component } from './_pages/webhims/master/other_masters1/sub-group2/sub-group2.component';
import { DoctorIncentiveMasterComponent } from './_pages/webhims/master/other_masters1/doctor-incentive-master/doctor-incentive-master.component';
import { RefDoctorIncentiveMasterComponent } from './_pages/webhims/master/other_masters1/ref-doctor-incentive-master/ref-doctor-incentive-master.component';
import { InsuranceMasterComponent } from './_pages/webhims/master/other_masters1/insurance-master/insurance-master.component';
import { CashlessAuthorizedComponent } from './_pages/webhims/master/other_masters1/cashless-authorized/cashless-authorized.component';
import { DischargeSummaryComponent } from './_pages/webhims/master/other_masters1/discharge-summary/discharge-summary.component';
import { DischargeSummaryTemplateMasterComponent } from './_pages/webhims/master/other_masters1/discharge-summary-template-master/discharge-summary-template-master.component';
import { DoctorHoursAbsenteeEntryComponent } from './_pages/webhims/master/other_masters1/doctor-hours-absentee-entry/doctor-hours-absentee-entry.component';
import { DeathSummaryComponent } from './_pages/webhims/master/other_masters1/death-summary/death-summary.component';
import { TdsMasterComponent } from './_pages/webhims/master/other_masters1/tds-master/tds-master.component';
import { CasepaperMedicineMasterComponent } from './_pages/webhims/master/other_masters1/casepaper-medicine-master/casepaper-medicine-master.component';

import { OrganizationComponent } from './_pages/webhims/master/other_masters2/organization/organization.component';
import { TpaMasterComponent } from './_pages/webhims/master/other_masters2/tpa-master/tpa-master.component';
import { ClassMasterComponent } from './_pages/webhims/master/other_masters2/class-master/class-master.component';
import { WardMasterComponent } from './_pages/webhims/master/other_masters2/ward-master/ward-master.component';
import { PartyMasterComponent } from './_pages/webhims/master/other_masters2/party-master/party-master.component';
import { PlasticMoneyMasterComponent } from './_pages/webhims/master/other_masters2/plastic-money-master/plastic-money-master.component';
import { PatientTypeMatserComponent } from './_pages/webhims/master/other_masters2/patient-type-matser/patient-type-matser.component';
import { DoctorPayableEntryComponent } from './_pages/webhims/master/other_masters2/doctor-payable-entry/doctor-payable-entry.component';
import { ExternalLabMasterComponent } from './_pages/webhims/master/other_masters2/external-lab-master/external-lab-master.component';
import { ExternalRadioMasterComponent } from './_pages/webhims/master/other_masters2/external-radio-master/external-radio-master.component';
import { ExternalLabTestMasterComponent } from './_pages/webhims/master/other_masters2/external-lab-test-master/external-lab-test-master.component';
import { ExternalRadioTestMasterComponent } from './_pages/webhims/master/other_masters2/external-radio-test-master/external-radio-test-master.component';

//IPD Dashboard
import { IpdDashboardComponent } from './_pages/webipd/ipd-dashboard/ipd-dashboard.component';
import { IpdDischargeSummeryComponent } from './_pages/webipd/ipd-discharge-summery/ipd-discharge-summery.component';
import { IpdDeathSummeryComponent } from './_pages/webipd/ipd-death-summery/ipd-death-summery.component';

import { LicenseLoginComponent } from './license-login/license-login.component';

import { ConsoleUserManagementComponent } from './_pages/webuser/console-user-management/console-user-management.component';
import { UserManagementComponent } from './_pages/webuser/user-management/user-management.component';
import { NumberSequenceComponent } from './_pages/webuser/number-sequence/number-sequence.component';
import { RolePermissionManagementComponent } from './_pages/webuser/role-permission-management/role-permission-management.component';

import { PathoLabReportComponent } from './_pages/webpatho/patho-lab-report/patho-lab-report.component';
import { PathoExternalLabTestReportComponent } from './_pages/webpatho/patho-external-lab-test-report/patho-external-lab-test-report.component';
import { PathoLabTestCountReportComponent } from './_pages/webpatho/patho-lab-test-count-report/patho-lab-test-count-report.component';
import { PathoGroupMasterComponent } from './_pages/webpatho/patho-group-master/patho-group-master.component';
import { PathoFormatMasterComponent } from './_pages/webpatho/patho-format-master/patho-format-master.component';
import { PathoObservationMasterComponent } from './_pages/webpatho/patho-observation-master/patho-observation-master.component';
import { PathoExternalLabMasterComponent } from './_pages/webpatho/patho-external-lab-master/patho-external-lab-master.component';
import { PathoExternalLabTestMasterComponent } from './_pages/webpatho/patho-external-lab-test-master/patho-external-lab-test-master.component';
import { PathoLisMasterComponent } from './_pages/webpatho/patho-lis-master/patho-lis-master.component';
import { PathoMachineMasterComponent } from './_pages/webpatho/patho-machine-master/patho-machine-master.component';
import { PathoSampleDeviceMasterComponent } from './_pages/webpatho/patho-sample-device-master/patho-sample-device-master.component';
import { PathoTemplateMasterComponent } from './_pages/webpatho/patho-template-master/patho-template-master.component';
import { PathoFormatTestMasterComponent } from './_pages/webpatho/patho-format-test-master/patho-format-test-master.component';
import { PathoStatusMasterComponent } from './_pages/webpatho/patho-status-master/patho-status-master.component';
import { PathoFooterUpdationComponent } from './_pages/webpatho/patho-footer-updation/patho-footer-updation.component';
import { PathoUtilityUpdateSampleComponent } from './_pages/webpatho/patho-utility-update-sample/patho-utility-update-sample.component';

import { ThemeToggleComponent } from './components/theme-toggle/theme-toggle.component';
import { DoctorIncentiveReportComponent } from './_pages/webhims/report/routine_report/doctor-incentive-report/doctor-incentive-report.component';
import { PathoFormatGroupMasterComponent } from './_pages/webpatho/patho-format-group-master/patho-format-group-master.component';
import { NavBarComponent } from './components/nav-bar/nav-bar.component';
import { HimsHomeComponent } from './home/hims-home/hims-home.component';
import { IpdHomeComponent } from './home/ipd-home/ipd-home.component';
import { PathoHomeComponent } from './home/patho-home/patho-home.component';
import { UserHomeComponent } from './home/user-home/user-home.component';
import { NoAccessComponent } from './components/no-access/no-access.component';
import { SideBarComponent } from './components/side-bar/side-bar.component';
import { ButtonSubmitDirective } from './_directive/button-submit.directive';
import { AccountYearManagementComponent } from './_pages/webuser/account-year-management/account-year-management.component';
import { TimeFormatPipe } from './_directive/time-format.pipe';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,

    RegistrationComponent,

    AppointmentComponent,

    AdmissionComponent,

    ScannedImageComponent,

    OpdComponent,

    RadioComponent,

    LabComponent,

    OpdhealthCheckupComponent,

    CashDuesComponent,

    OpdRefundComponent,

    RadioRefundComponent,

    LabRefundComponent,

    OpdhealthCheckupRefundComponent,

    BillDetailSheetComponent,

    ProvisionalBillComponent,

    FinalIpdBillComponent,

    BedShiftingComponent,

    BedStatusComponent,

    VacantBedComponent,

    BedDischargeComponent,

    DischargeClearanceComponent,

    ReceiptComponent,

    PaymentComponent,

    IpdRegisterComponent,

    IpdReceiptRegisterComponent,

    OpdRegisterComponent,

    LabRegisterComponent,

    RadioRegisterComponent,

    HealthCheckupRegisterComponent,

    CashDueReportComponent,

    OrganizationReportComponent,

    MisComponent,

    GroupwiseTestwiseAnalysisComponent,

    AdmissionDischargeComponent,

    FeeCollectionReportComponent,

    IpdTestComponent,

    OpdTestComponent,

    ConsultantDoctorComponent,

    ReferringDoctorComponent,

    OpdBillMasterComponent,

    LabBillMasterComponent,

    RadioBillMasterComponent,

    AppointmentSchedulerComponent,

    IpdPackageMasterComponent,

    OpdPackageMasterComponent,

    DepartmentComponent,

    SubdepartmentComponent,

    ConcessionComponent,

    SuperGroupComponent,

    GroupComponent,

    SubGroupComponent,

    SubGroup1Component,

    SubGroup2Component,

    DoctorIncentiveMasterComponent,

    RefDoctorIncentiveMasterComponent,

    InsuranceMasterComponent,

    CashlessAuthorizedComponent,

    DischargeSummaryComponent,

    DischargeSummaryTemplateMasterComponent,

    DoctorHoursAbsenteeEntryComponent,

    DeathSummaryComponent,

    TdsMasterComponent,

    CasepaperMedicineMasterComponent,

    OrganizationComponent,

    TpaMasterComponent,

    ClassMasterComponent,

    WardMasterComponent,

    PartyMasterComponent,

    PlasticMoneyMasterComponent,

    PatientTypeMatserComponent,

    DoctorPayableEntryComponent,

    ExternalLabMasterComponent,

    ExternalRadioMasterComponent,

    ExternalLabTestMasterComponent,

    ExternalRadioTestMasterComponent,

    IpdDashboardComponent,

    IpdDischargeSummeryComponent,
    IpdDeathSummeryComponent,
    LicenseLoginComponent,
    ConsoleUserManagementComponent,
    UserManagementComponent,
    NumberSequenceComponent,
    RolePermissionManagementComponent,
    ErrorComponent,
    PathoLabReportComponent,
    PathoExternalLabTestReportComponent,
    PathoLabTestCountReportComponent,
    PathoGroupMasterComponent,
    PathoFormatMasterComponent,
    PathoObservationMasterComponent,
    PathoExternalLabMasterComponent,
    PathoExternalLabTestMasterComponent,
    PathoLisMasterComponent,
    PathoMachineMasterComponent,
    PathoSampleDeviceMasterComponent,
    PathoTemplateMasterComponent,
    PathoFormatTestMasterComponent,
    PathoStatusMasterComponent,
    PathoFooterUpdationComponent,
    PathoUtilityUpdateSampleComponent,
    ThemeToggleComponent,
    DoctorIncentiveReportComponent,
    PathoFormatGroupMasterComponent,
    NavBarComponent,
    HimsHomeComponent,
    IpdHomeComponent,
    PathoHomeComponent,
    UserHomeComponent,
    NoAccessComponent,
    SideBarComponent,
    ButtonSubmitDirective,
    AccountYearManagementComponent,
    TimeFormatPipe,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    DataTablesModule,
    NgbModule,
    ReactiveFormsModule,
    NgSelectModule,
    PdfJsViewerModule,
    ToastrModule.forRoot(),
  ],
  providers: [authInterceptorProviders, Title],
  bootstrap: [AppComponent],
})
export class AppModule { }
