**Generate Component**
ng generate component components/nav-bar --skipTests=true --module app

ng generate component home/hims-home --skipTests=true --module app
ng generate component home/ipd-home --skipTests=true --module app
ng generate component home/patho-home --skipTests=true --module app
ng generate component home/user-home --skipTests=true --module app

{{{{{{{{{{{{{{{{{Reception}}}}}}}}}}}}}}}}}
*************reception_reception*************
ng generate component _pages/reception/reception_reception/registration --module app
ng generate component _pages/reception/reception_reception/appointment --module app
ng generate component _pages/reception/reception_reception/admission --module app
ng generate component _pages/reception/reception_reception/scanned-image --module app

*************reception_bill****************
ng generate component _pages/reception/reception_bill/opd --module app ****
ng generate component _pages/reception/reception_bill/radio --module app ****
ng generate component _pages/reception/reception_bill/lab --module app ****
ng generate component _pages/reception/reception_bill/opdhealth-checkup --module app ****
ng generate component _pages/reception/reception_bill/cash-dues --module app ****

*****************reception_bill_refund*************
ng generate component _pages/reception/reception_bill_refund/opd_refund --module app ****
ng generate component _pages/reception/reception_bill_refund/radio_refund --module app ****
ng generate component _pages/reception/reception_bill_refund/lab_refund --module app ****
ng generate component _pages/reception/reception_bill_refund/opdhealth-checkup_refund --module app ****



{{{{{{{{{{{{{{{IPD}}}}}}}}}}}}}}}

*******************Bill*****************
ng generate component _pages/ipd/bill/bill_detail_sheet --module app ****
ng generate component _pages/ipd/bill/provisional_bill --module app ****
ng generate component _pages/ipd/bill/final_ipd_bill --module app ****
ng generate component _pages/ipd/bill/ipd_advanced --module app
ng generate component _pages/ipd/bill/payment_refund --module app

*******************Bed*****************
ng generate component _pages/ipd/bed/bed_shifting --module app
ng generate component _pages/ipd/bed/bed_status --module app
ng generate component _pages/ipd/bed/vacant_bed --module app

*******************Dischange*****************
ng generate component _pages/ipd/dischange/bed_discharge --module app
ng generate component _pages/ipd/dischange/discharge_clearance --module app ****


{{{{{{{{{{{{{{{Account}}}}}}}}}}}}}}}

*******************Entry*****************
ng generate component _pages/account/entry/receipt --module app ****
ng generate component _pages/account/entry/payment --module app ****


{{{{{{{{{{{{{{{Reports}}}}}}}}}}}}}}}

*******************Register Report*****************
ng generate component _pages/report/register_report/ipd_register --module app
ng generate component _pages/report/register_report/ipd_receipt_register --module app
ng generate component _pages/report/register_report/opd_register --module app ****||||
ng generate component _pages/report/register_report/lab_register --module app ****||||
ng generate component _pages/report/register_report/radio_register --module app ****||||
ng generate component _pages/report/register_report/health_checkup_register --module app ****
ng generate component _pages/report/register_report/cash_due_report --module app
ng generate component _pages/report/register_report/organization_report --module app


*******************Analysis Report*****************
ng generate component _pages/report/analysis_report/mis --module app
ng generate component _pages/report/analysis_report/groupwise_testwise_analysis --module app

*******************Routine Reports*****************
ng generate component _pages/report/routine_report/admission_discharge --module app
ng generate component _pages/report/routine_report/fee_collection_report --module app
ng generate component _pages/webhims/report/routine_report/doctor_incentive_report --module app
DOCTOR INCENTIVE REPORT

{{{{{{{{{{{{{{{Masters}}}}}}}}}}}}}}}

*******************Test Master*****************
ng generate component _pages/master/test_master/ipd --module app ****
ng generate component _pages/master/test_master/opd --module app ****

*******************Doctor*****************
ng generate component _pages/master/doctor/consultant_doctor --module app ****
ng generate component _pages/master/doctor/referring_doctor --module app ****

*******************Bill Master*****************
ng generate component _pages/master/bill_master/opd_bill_master --module app
ng generate component _pages/master/bill_master/lab_bill_master --module app
ng generate component _pages/master/bill_master/radio_bill_master --module app

*******************Appointment Master*****************
ng generate component _pages/master/appointment_master/appointment_scheduler --module app ****

*******************Package Master*****************
ng generate component _pages/master/package_master/ipd_package_master --module app ****
ng generate component _pages/master/package_master/opd_package_master --module app ****

*******************Other Masters1*****************
ng generate component _pages/master/other_masters1/department --module app ****
ng generate component _pages/master/other_masters1/subdepartment --module app ****
ng generate component _pages/master/other_masters1/concession --module app ****
ng generate component _pages/master/other_masters1/super_group --module app ****
ng generate component _pages/master/other_masters1/group --module app ****
ng generate component _pages/master/other_masters1/sub_group --module app ****
ng generate component _pages/master/other_masters1/sub_group1 --module app ****
ng generate component _pages/master/other_masters1/sub_group2 --module app ****
ng generate component _pages/master/other_masters1/doctor_incentive_master --module app ####
ng generate component _pages/master/other_masters1/ref_doctor_incentive_master --module app ####
ng generate component _pages/master/other_masters1/insurance_master --module app ****
ng generate component _pages/master/other_masters1/cashless_authorized --module app ****
ng generate component _pages/master/other_masters1/discharge_summary --module app ****
ng generate component _pages/master/other_masters1/discharge_summary_template_master --module app ****
ng generate component _pages/master/other_masters1/doctor_hours_absentee_entry --module app
ng generate component _pages/master/other_masters1/death_summary --module app ****
ng generate component _pages/master/other_masters1/tds_master --module app ****
ng generate component _pages/master/other_masters1/casepaper_medicine_master --module app ****

*******************Other Masters2*****************
ng generate component _pages/master/other_masters2/organization --module app
ng generate component _pages/master/other_masters2/tpa_master --module app ****
ng generate component _pages/master/other_masters1/class_master --module app ****
ng generate component _pages/master/other_masters1/ward_master --module app ****
ng generate component _pages/master/other_masters1/party_master --module app ****
ng generate component _pages/master/other_masters1/plastic_money_master --module app ****
ng generate component _pages/master/other_masters1/patient_type_matser --module app 
ng generate component _pages/master/other_masters1/doctor_payable_entry --module app
ng generate component _pages/master/other_masters1/external_lab_master --module app ****
ng generate component _pages/master/other_masters1/external_radio_master --module app ****
ng generate component _pages/master/other_masters1/external_lab_test_master --module app
ng generate component _pages/master/other_masters1/external_radio_test_master --module app


{{{{{{{{{{{{{{{IPD Dashboard}}}}}}}}}}}}}}}

*******************Main*****************
ng generate component _pages/webipd/ipd_dashboard --module app

ng generate component _pages/webipd/ipd_death_summery --module app
ng generate component _pages/webipd/ipd_discharge_summery --module app



{{{{{{{{{{{{{{{ User Management }}}}}}}}}}}}}}}

ng generate component _pages/webuser/console_user_management --module app
ng generate component _pages/webuser/user_management --module app
ng generate component _pages/webuser/number_sequence --module app
ng generate component _pages/webuser/role_permission_management --module app
ng generate component _pages/webuser/account_year_management --module app    




--------------------------------------------------------------------------------------------------------
                    PATHO
--------------------------------------------------------------------------------------------------------


ng generate component _pages/webpatho/patho_lab_report --skipTests=true --module app

ng generate component _pages/webpatho/patho_external_lab_test_report --skipTests=true --module app
ng generate component _pages/webpatho/patho_lab_test_count_report --skipTests=true --module app

ng generate component _pages/webpatho/patho_group_master --skipTests=true --module app
ng generate component _pages/webpatho/patho_format_master --skipTests=true --module app
ng generate component _pages/webpatho/patho_observation_master --skipTests=true --module app
ng generate component _pages/webpatho/patho_external_lab_master --skipTests=true --module app
ng generate component _pages/webpatho/patho_external_lab_test_master --skipTests=true --module app
ng generate component _pages/webpatho/patho_lis_master --skipTests=true --module app
ng generate component _pages/webpatho/patho_machine_master --skipTests=true --module app
ng generate component _pages/webpatho/patho_sample_device_master --skipTests=true --module app
ng generate component _pages/webpatho/patho_template_master --skipTests=true --module app
ng generate component _pages/webpatho/patho_format_test_master --skipTests=true --module app
ng generate component _pages/webpatho/patho_status_master --skipTests=true --module app
ng generate component _pages/webpatho/patho_footer_updation --skipTests=true --module app
ng generate component _pages/webpatho/patho_utility_update_sample --skipTests=true --module app

ng generate component _pages/webpatho/patho_format_group_master --skipTests=true --module app



ng generate pipe _directive/time_format