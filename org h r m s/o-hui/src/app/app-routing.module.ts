import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { AuthGuard } from './auth.guard';
import { ErrorComponent } from './components/error/error.component';

// reception
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

// ipd
import { BillDetailSheetComponent } from './_pages/webhims/ipd/bill/bill-detail-sheet/bill-detail-sheet.component';
import { ProvisionalBillComponent } from './_pages/webhims/ipd/bill/provisional-bill/provisional-bill.component';
import { FinalIpdBillComponent } from './_pages/webhims/ipd/bill/final-ipd-bill/final-ipd-bill.component';

import { BedShiftingComponent } from './_pages/webhims/ipd/bed/bed-shifting/bed-shifting.component';
import { BedStatusComponent } from './_pages/webhims/ipd/bed/bed-status/bed-status.component';
import { VacantBedComponent } from './_pages/webhims/ipd/bed/vacant-bed/vacant-bed.component';

import { BedDischargeComponent } from './_pages/webhims/ipd/dischange/bed-discharge/bed-discharge.component';
import { DischargeClearanceComponent } from './_pages/webhims/ipd/dischange/discharge-clearance/discharge-clearance.component';

// account
import { ReceiptComponent } from './_pages/webhims/account/entry/receipt/receipt.component';
import { PaymentComponent } from './_pages/webhims/account/entry/payment/payment.component';

// report
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
import { DoctorIncentiveReportComponent } from './_pages/webhims/report/routine_report/doctor-incentive-report/doctor-incentive-report.component';

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

//User Management
import { ConsoleUserManagementComponent } from './_pages/webuser/console-user-management/console-user-management.component';
import { UserManagementComponent } from './_pages/webuser/user-management/user-management.component';
import { NumberSequenceComponent } from './_pages/webuser/number-sequence/number-sequence.component';
import { RolePermissionManagementComponent } from './_pages/webuser/role-permission-management/role-permission-management.component';
import { AccountYearManagementComponent } from './_pages/webuser/account-year-management/account-year-management.component';

//Patho Module
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
import { PathoFormatGroupMasterComponent } from './_pages/webpatho/patho-format-group-master/patho-format-group-master.component';
import { HimsHomeComponent } from './home/hims-home/hims-home.component';
import { IpdHomeComponent } from './home/ipd-home/ipd-home.component';
import { PathoHomeComponent } from './home/patho-home/patho-home.component';
import { UserHomeComponent } from './home/user-home/user-home.component';

const routes: Routes = [

  { path: '', redirectTo: 'portal-login', pathMatch: 'full' },

  { path: 'portal-login', component: LicenseLoginComponent },
  { path: 'login', component: LoginComponent },
  { path: 'error', component: ErrorComponent },
  { path: 'login/user', component: LoginComponent },

  { path: 'hims/home', component: HimsHomeComponent },
  { path: 'ipd/home', component: IpdHomeComponent },
  { path: 'patho/home', component: PathoHomeComponent },
  { path: 'user/home', component: UserHomeComponent },

  { path: 'register', component: RegisterComponent },


  /**
   * ===============================================================
   * Reception Start <<<<<<<<<<<<<<<<
   * ===============================================================
  */

  /**
   * reception_reception <<<<<<<<<<<<<<
  */
  {
    path: 'hims/reception-reception-registration', component: RegistrationComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'hims/reception-reception-appointment', component: AppointmentComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'hims/reception-reception-admission', component: AdmissionComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'hims/reception-reception-scannedimage', component: ScannedImageComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  /**
  * reception_reception >>>>>>>>>>>>
 */

  /**
    * reception_bill <<<<<<<<<<<<<<
   */
  {
    path: 'hims/reception-bill-opd', component: OpdComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'hims/reception-bill-radio', component: RadioComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'hims/reception-bill-lab', component: LabComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },

  {
    path: 'hims/reception-bill-opdhealth_checkup', component: OpdhealthCheckupComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'hims/reception-bill-cash_dues', component: CashDuesComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },

  /**
  * reception_bill >>>>>>>>>>>>>
 */


  /**
    * reception_bill_refund <<<<<<<<<<<<<
   */

  {
    path: 'hims/reception-bill_refund-opd_refund', component: OpdRefundComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'hims/reception-bill_refund-radio_refund', component: RadioRefundComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'hims/reception-bill_refund-lab_refund', component: LabRefundComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'hims/reception-bill_refund-opdhealth_checkup_refund', component: OpdhealthCheckupRefundComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },

  /**
    * reception_bill_refund >>>>>>>>>>>>>
   */

  /**
   * ===============================================================
   * Reception End >>>>>>>>>>>>>>>>>>>>>>
   * ===============================================================
  */


  /**
   * ===============================================================
   * IPD Start <<<<<<<<<<<<<<<<
   * ===============================================================
  */

  /**
    * Bill <<<<<<<<<<<<<<
   */
  {
    path: 'hims/ipd-bill-bill_detail_sheet', component: BillDetailSheetComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'hims/ipd-bill-provisional_bill', component: ProvisionalBillComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'hims/ipd-bill-final_ipd_bill', component: FinalIpdBillComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  // {
  //   path: 'hims/account-entry-receipt', component: ReceiptComponent,
  //   canActivate: [AuthGuard],
  //   data: {
  //     role: 'ROLE_ADMIN'
  //   }
  // },
  // {
  //   path: 'hims/ipd-bill-payment_refund', component: PaymentComponent,
  //   canActivate: [AuthGuard],
  //   data: {
  //     role: 'ROLE_ADMIN'
  //   }
  // },
  /**
    * Bill >>>>>>>>>>>>>
   */

  /**
   * Bed <<<<<<<<<<<<<<<<<<<<<<
  */
  {
    path: 'hims/ipd-bed-bed_shifting', component: BedShiftingComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'hims/ipd-bed-bed_status', component: BedStatusComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'hims/ipd-bed-vacant_bed', component: VacantBedComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  /**
    * Bed >>>>>>>>>>>>>>>>>>>>
   */

  /**
   * dischange <<<<<<<<<<<<<<<<<<<<
  */
  {
    path: 'hims/ipd-dischange-bed_discharge', component: BedDischargeComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'hims/ipd-dischange-discharge_clearance', component: DischargeClearanceComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  /**
    * dischange >>>>>>>>>>>>>>>>>>>>
   */

  /**
   * ===============================================================
   * IPD End >>>>>>>>>>>>>>>>>>>>
   * ===============================================================
  */



  /**
   * ===============================================================
   * Account Start <<<<<<<<<<<<<<<
   * ===============================================================
  */

  /**
    * entry <<<<<<<<<<<<<<<<<<
   */

  {
    path: 'hims/account-entry-receipt', component: ReceiptComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'hims/account-entry-payment', component: PaymentComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },

  /**
   * entry >>>>>>>>>>>>>>>>>>>>
  */


  /**
    * ===============================================================
    * Account End >>>>>>>>>>>>>>>
    * ===============================================================
   */

  /**
    * ===============================================================
    * Report Start >>>>>>>>>>>>>>>
    * ===============================================================
   */

  /**
 * register_report <<<<<<<<<<<<<<<<<<
*/
  {
    path: 'hims/report-register_report-ipd_register', component: IpdRegisterComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'hims/report-register_report-ipd_receipt_register', component: IpdReceiptRegisterComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'hims/report-register_report-opd_register', component: OpdRegisterComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'hims/report-register_report-lab_register', component: LabRegisterComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'hims/report-register_report-radio_register', component: RadioRegisterComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'hims/report-register_report-health_checkup_register', component: HealthCheckupRegisterComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'hims/report-register_report-cash_due_report', component: CashDueReportComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'hims/report-register_report-organization_report', component: OrganizationReportComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  /**
  * register_report >>>>>>>>>>>>>>>>>>>>
 */

  /**
 * analysis_report <<<<<<<<<<<<<<<<<<<<<
*/
  {
    path: 'hims/report-analysis_report-mis', component: MisComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'hims/report-analysis_report-groupwise_testwise_analysis', component: GroupwiseTestwiseAnalysisComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  /**
  * analysis_report >>>>>>>>>>>>>>>>>>>>
 */

  /**
 * routine_report >>>>>>>>>>>>>>>>>>>>
*/
  {
    path: 'hims/report-routine_report-admission_discharge', component: AdmissionDischargeComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'hims/report-routine_report-fee_collection_report', component: FeeCollectionReportComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'hims/report_routine_doctor_incentive_report', component: DoctorIncentiveReportComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },

  /**
 * routine_report >>>>>>>>>>>>>>>>>>>>
*/

  /**
   * ===============================================================
   * Report End >>>>>>>>>>>>>>>
   * ===============================================================
  */



  /**
   * ===============================================================
   * Masters Start <<<<<<<<<<<<<<<<<<<<<
   * ===============================================================
  */


  /**
* test_master <<<<<<<<<<<<<<<<<<<
*/
  {
    path: 'hims/master-test_master-ipd_test', component: IpdTestComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'hims/master-test_master-opd_test', component: OpdTestComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  /**
* test_master >>>>>>>>>>>>>>>>>>>>
*/

  /**
 * doctor <<<<<<<<<<<<<<<<<<<
*/
  {
    path: 'hims/master-doctor-consultant_doctor', component: ConsultantDoctorComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'hims/master-doctor-referring_doctor', component: ReferringDoctorComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  /**
* doctor >>>>>>>>>>>>>>>>>>>>
*/

  /**
* bill_master <<<<<<<<<<<<<<<<<<<
*/
  {
    path: 'hims/master-bill_master-opd_bill_master', component: OpdBillMasterComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'hims/master-bill_master-lab_bill_master', component: LabBillMasterComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'hims/master-bill_master-radio_bill_master', component: RadioBillMasterComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  /**
* bill_master >>>>>>>>>>>>>>>>>>>>
*/

  /**
 * appointment_master <<<<<<<<<<<<<<<<<<<
*/
  {
    path: 'hims/master-appointment_master-appointment_scheduler', component: AppointmentSchedulerComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  /**
* appointment_master >>>>>>>>>>>>>>>>>>>>
*/

  /**
* package_master <<<<<<<<<<<<<<<<<<<
*/
  {
    path: 'hims/master-package_master-ipd_package_master', component: IpdPackageMasterComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'hims/master-package_master-opd_package_master', component: OpdPackageMasterComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  /**
* package_master >>>>>>>>>>>>>>>>>>>>
*/

  /**
* other_masters1 <<<<<<<<<<<<<<<<<<<
*/
  {
    path: 'hims/master-other_masters1-department', component: DepartmentComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'hims/master-other_masters1-subdepartment', component: SubdepartmentComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'hims/master-other_masters1-concession', component: ConcessionComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'hims/master-other_masters1-super_group', component: SuperGroupComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'hims/master-other_masters1-group', component: GroupComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'hims/master-other_masters1-sub_group', component: SubGroupComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'hims/master-other_masters1-sub_group1', component: SubGroup1Component,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'hims/master-other_masters1-sub_group2', component: SubGroup2Component,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'hims/master-other_masters1-doctor_incentive_master', component: DoctorIncentiveMasterComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'hims/master-other_masters1-ref_doctor_incentive_master', component: RefDoctorIncentiveMasterComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'hims/master-other_masters1-insurance_master', component: InsuranceMasterComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'hims/master-other_masters1-cashless_authorized', component: CashlessAuthorizedComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'hims/master-other_masters1-discharge_summary', component: DischargeSummaryComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'hims/master-other_masters1-discharge_summary_template_master', component: DischargeSummaryTemplateMasterComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'hims/master-other_masters1-doctor_hours_absentee_entry', component: DoctorHoursAbsenteeEntryComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'hims/master-other_masters1-death_summary', component: DeathSummaryComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'hims/master-other_masters1-tds_master', component: TdsMasterComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'hims/master-other_masters1-casepaper_medicine_master', component: CasepaperMedicineMasterComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  /**
* other_masters1 >>>>>>>>>>>>>>>>>>>>
*/

  /**
* other_masters2 <<<<<<<<<<<<<<<<<<<
*/
  {
    path: 'hims/master-other_masters2-organization', component: OrganizationComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'hims/master-other_masters2-tpa_master', component: TpaMasterComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'hims/master-other_masters2-class_master', component: ClassMasterComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'hims/master-other_masters2-ward_master', component: WardMasterComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'hims/master-other_masters2-party_master', component: PartyMasterComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'hims/master-other_masters2-plastic_money_master', component: PlasticMoneyMasterComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'hims/master-other_masters2-patient_type_matser', component: PatientTypeMatserComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'hims/master-other_masters2-doctor_payable_entry', component: DoctorPayableEntryComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'hims/master-other_masters2-external_lab_master', component: ExternalLabMasterComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'hims/master-other_masters2-external_radio_master', component: ExternalRadioMasterComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'hims/master-other_masters2-external_lab_test_master', component: ExternalLabTestMasterComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'hims/master-other_masters2-external_radio_test_master', component: ExternalRadioTestMasterComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  /**
* other_masters2 >>>>>>>>>>>>>>>>>>>>
*/

  /**
    * ===============================================================
    * Masters End >>>>>>>>>>>>>>>
    * ===============================================================
   */


  /**
    * ===============================================================
    * IPD Dashboard Start <<<<<<<<<<<<<<<<<<<<
    * ===============================================================
   */
  {
    path: 'ipd/webipd_ipddashboard', component: IpdDashboardComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },

  {
    path: 'ipd/webipd_discharge_summery', component: IpdDischargeSummeryComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'ipd/webipd_death_summery', component: IpdDeathSummeryComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  /**
    * ===============================================================
    * IPD Dashobard End >>>>>>>>>>>>>>>
    * ===============================================================
   */





  /**
    * ===============================================================
    * User Management Start <<<<<<<<<<<<<<<<<<<<
    * ===============================================================
   */

  {
    path: 'user/console_user_management', component: ConsoleUserManagementComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'user/user_management', component: UserManagementComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'user/number_sequence', component: NumberSequenceComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'user/role_permission_management', component: RolePermissionManagementComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'user/account_year_management', component: AccountYearManagementComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  
  /**
    * ===============================================================
    * User Management End <<<<<<<<<<<<<<<<<<<<
    * ===============================================================
   */







  /**
    * ===============================================================
    * Patho Start <<<<<<<<<<<<<<<<<<<<
    * ===============================================================
   */
  {
    path: 'patho/patho_lab_report', component: PathoLabReportComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'patho/patho_external_lab_test_report', component: PathoExternalLabTestReportComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'patho/patho_lab_test_count_report', component: PathoLabTestCountReportComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'patho/patho_group_master', component: PathoGroupMasterComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'patho/patho_format_master', component: PathoFormatMasterComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'patho/patho_observation_master', component: PathoObservationMasterComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'patho/patho_format_group_master', component: PathoFormatGroupMasterComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'patho/patho_external_lab_master', component: PathoExternalLabMasterComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'patho/patho_external_lab_test_master', component: PathoExternalLabTestMasterComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'patho/patho_lis_master', component: PathoLisMasterComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'patho/patho_machine_master', component: PathoMachineMasterComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'patho/patho_sample_device_master', component: PathoSampleDeviceMasterComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'patho/patho_template_master', component: PathoTemplateMasterComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'patho/patho_format_test_master', component: PathoFormatTestMasterComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'patho/patho_status_master', component: PathoStatusMasterComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'patho/patho_footer_updation', component: PathoFooterUpdationComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },
  {
    path: 'patho/patho_utility_update_sample', component: PathoUtilityUpdateSampleComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'ROLE_ADMIN'
    }
  },

  /**
    * ===============================================================
    * Patho End <<<<<<<<<<<<<<<<<<<<
    * ===============================================================
   */


  /*
 *End Dev >>>>>>>>>>>>>>>>>>>>
 */




];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
