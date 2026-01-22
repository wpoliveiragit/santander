# Santander- Desafio OSA
[Link do desafio](src/main/resources/doc/desafio.md)

**Observação:** O projeto possui apenas o que foi solicitado e contendo convenções de programação

- [] dasdas

O projeto possui 3 endpoints

Geração de token
```bash
curl --location 'http://localhost:8080/oauth/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--header 'Cookie: JSESSIONID=4BE26A61FEEFADACBF97DFD803554D0B' \
--data-urlencode 'username=thon'
```

Cadastro de agência
```bash
curl -X POST http://localhost:8080/desafio/cadastrar \
-H "Authorization: Bearer eyJraWQiOiJteS1rZXktaWQiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ0aG9uIiwiaWF0IjoxNzYxMzQ2MDUxLCJzY29wZSI6InJlYWQgd3JpdGUifQ.mGhDrNYzYf7aHwHygDdlLJqpRiW5yz3pH_j_IBxFPq1Z7WzwR_ncv9Q84rsxDPwROn-Ud7gFrNnS9tuqY6cLinckSOMV3kLLXcV30GpN1T-tUF3D6lnFa_fWnfUqwMeoerpaIheEx2KIJcEE_5RNSWPU15M2BtdGcWX_zCyUuEsk0moLSHsaMLBTpMkQcFLjbuUl2b8fnWSNycRNibk96iu34GmnSdsWrkgXSXFfn3_AfInG4SP1PpxaKArKzGgf12oUWMhTwAqohx3y7zummK28iTa0dtn1ieLekqE_zgNC9p2dixJpGI3xPvsZZYM0ekeH4PHVFDeHDjDblqxfGQ" \
-H "Content-Type: application/json" \
-d '{
"name": "Agencia Central",
"posX": 12.34,
"posY": 56.78
}'
```


```bash
curl -X GET "http://localhost:8080/desafio/distancia?posX=10&posY=20" \
  -H "Authorization: Bearer eyJraWQiOiJteS1rZXktaWQiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ0aG9uIiwiaWF0IjoxNzYxMzQ2MDUxLCJzY29wZSI6InJlYWQgd3JpdGUifQ.mGhDrNYzYf7aHwHygDdlLJqpRiW5yz3pH_j_IBxFPq1Z7WzwR_ncv9Q84rsxDPwROn-Ud7gFrNnS9tuqY6cLinckSOMV3kLLXcV30GpN1T-tUF3D6lnFa_fWnfUqwMeoerpaIheEx2KIJcEE_5RNSWPU15M2BtdGcWX_zCyUuEsk0moLSHsaMLBTpMkQcFLjbuUl2b8fnWSNycRNibk96iu34GmnSdsWrkgXSXFfn3_AfInG4SP1PpxaKArKzGgf12oUWMhTwAqohx3y7zummK28iTa0dtn1ieLekqE_zgNC9p2dixJpGI3xPvsZZYM0ekeH4PHVFDeHDjDblqxfGQ"
```