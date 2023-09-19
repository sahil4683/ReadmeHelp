**Generate Service**

{{{{{{{{{{{{{{{{{Static}}}}}}}}}}}}}}}}}
ng generate service _service/static/bedmaster
ng generate service _service/static/organization_master   
ng generate service _service/static/doctor_reference   
ng generate service _service/static/consultant_master
ng generate service _service/static/master_category

{{{{{{{{{{{{{{{{{Static}}}}}}}}}}}}}}}}}


{{{{{{{{{{{{{{{{{Reception}}}}}}}}}}}}}}}}}
*************reception_reception*************
ng generate service _service/reception/reception_reception/registration
ng generate service _service/reception/reception_reception/admission

{{{{{{{{{{{{{{{IPD}}}}}}}}}}}}}}}

*******************Bill*****************
ng generate service _service/ipd/bill/ipd_advanced




ng generate service _service/account/entry/receipt
ng generate service _service/account/entry/payment

ng generate service _service/master/other_masters1/department
ng generate service _service/master/other_masters1/subdepartment
ng generate service _service/master/other_masters1/plastic_money_master
ng generate service _service/master/other_masters1/class_master
ng generate service _service/master/other_masters1/ward_master
ng generate service _service/master/other_masters1/super_group
ng generate service _service/master/other_masters1/group
ng generate service _service/master/other_masters1/sub_group
ng generate service _service/master/other_masters1/sub_group1
ng generate service _service/master/other_masters1/sub_group2
ng generate service _service/master/other_masters1/insurance_master
ng generate service _service/master/other_masters1/tds_master
ng generate service _service/master/other_masters1/casepaper_medicine_master
ng generate service _service/master/other_masters1/external_lab_master
ng generate service _service/master/other_masters1/external_radio_master
ng generate service _service/master/other_masters1/concession

ng generate service _service/master/package_master/ipd_package_master
ng generate service _service/master/package_master/opd_package_master

ng generate service _service/master/appointment_master/appointment_scheduler

ng generate service _service/master/test_master/ipd
ng generate service _service/master/test_master/opd

ng generate service _service/master/other_masters1/death_summary
ng generate service _service/master/other_masters1/discharge_summary
ng generate service _service/master/other_masters1/discharge_summary_template_master

ng generate service _service/reception/reception_bill/opd
ng generate service _service/reception/reception_bill/radio
ng generate service _service/reception/reception_bill/lab
ng generate service _service/reception/reception_bill/opdhealth-checkup
ng generate service _service/reception/reception_bill/cash-dues


ng generate service _service/reception/reception_bill_refund/opd_refund
ng generate service _service/reception/reception_bill_refund/radio_refund
ng generate service _service/reception/reception_bill_refund/lab_refund
ng generate service _service/reception/reception_bill_refund/opdhealthcheckup_refund



ng generate service _service/static/common

ng generate service _service/master/other_masters2/tpa_master
ng generate service _service/master/other_masters1/party_master

ng generate service _service/master/other_masters1/account_group

ng generate service _service/report/register_report/cash_due_report

ng generate service _service/report/register_report/opd_register
ng generate service _service/report/register_report/health_checkup_register


ng generate service _service/ipd/dischange/discharge_clearance



ng generate service _service/report/routine_report
ng generate service _service/report/analysis_report



ng generate service _service/ipd/bill/bill_detail_sheet
ng generate service _service/ipd/bill/provisional_bill
ng generate service _service/ipd/bill/final_ipd_bill

ng generate service _service/ipd_dashboard/request_sheet_ipd

ng generate service _service/ipd_dashboard/ipd_discharge_summery
ng generate service _service/ipd_dashboard/ipd_death_summery
ng generate service _service/ipd_dashboard/diagnosis_master
ng generate service _service/ipd_dashboard/template_master

ng generate service _service/dashboard/dashboard_master





--------------------------------------------------------------------------------------------------------
                    PATHO
--------------------------------------------------------------------------------------------------------


ng generate service _service/webpatho/patho_lab_report --skipTests=true

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

ng generate service _service/webpatho/patho_format_test_master --skipTests=true
ng generate service _service/webpatho/patho_status_master --skipTests=true


ng generate service _service/webpatho/patho_footer_updation --skipTests=true
ng generate service _service/webpatho/patho_utility_update_sample --skipTests=true

ng generate service _service/webpatho/patho_category --skipTests=true
ng generate service _service/webpatho/patho_observation_master --skipTests=true

ng generate service _service/webhims/master/other_masters1/doctor_incentive_master --skipTests=true

ng generate service _helpers/component_reload --skipTests=true
ng generate service _helpers/datatable_options --skipTests=true

ng generate service _service/webhims/report/routine_report/doctor_incentive_report --skipTests=true

ng generate service _service/webpatho/patho_format_group_master --skipTests=true