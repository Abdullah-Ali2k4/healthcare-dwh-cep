SELECT dim_patient.gender,AVG(fact_visit.total_cost),
        fact_visit.age_group,fact_visit.diagnosis_name
        from fact_visit join dim_patient on dim_patient.patient_id = fact_visit.patient_id
        group by fact_visit.age_group,dim_patient.gender,fact_visit.diagnosis_name;

SELECT
    dim_outbreak.disease_name,
    dim_outbreak.outbreak_monthyear,
    dim_outbreak.zip AS outbreak_zip,
    fact_visitPerMonth.number_of_visit,
    dim_outbreak.number_of_outbreak
FROM dim_outbreak
JOIN fact_visitPerMonth
    ON dim_outbreak.outbreak_monthyear = fact_visitPerMonth.month_year
ORDER BY fact_visitPerMonth.number_of_visit DESC;

SELECT
    fact_visitPerMonth.month_year,
    fact_visitPerMonth.number_of_visit,
    dim_outbreak.disease_name,
    dim_outbreak.number_of_outbreak,
    CASE
        WHEN dim_outbreak.outbreak_monthyear IS NOT NULL THEN 'Yes'
        ELSE 'No'
    END AS outbreak_occurred
FROM fact_visitPerMonth
LEFT JOIN dim_outbreak
    ON fact_visitPerMonth.month_year = dim_outbreak.outbreak_monthyear
ORDER BY fact_visitPerMonth.month_year;

SELECT fact_medicine.med_id,dim_medication.med_name,
        fact_medicine.used_stock,fact_medicine.current_stock,
        fact_medicine.last_updated
        from fact_medicine
        join dim_medication
        on fact_medicine.med_id = dim_medication.med_id;
SELECT count(*)
        from fact_medicine
        join dim_medication
        on fact_medicine.med_id = dim_medication.med_id;