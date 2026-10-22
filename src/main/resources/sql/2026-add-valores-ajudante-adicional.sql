-- Script manual (não executado pela aplicação; produção usa ddl-auto=none).
ALTER TABLE clientes ADD COLUMN IF NOT EXISTS valor_ajudante_vuc   VARCHAR(12) NOT NULL DEFAULT '';
ALTER TABLE clientes ADD COLUMN IF NOT EXISTS valor_ajudante_toco  VARCHAR(12) NOT NULL DEFAULT '';
ALTER TABLE clientes ADD COLUMN IF NOT EXISTS adicional_mangueira  VARCHAR(12) NOT NULL DEFAULT '';
ALTER TABLE clientes ADD COLUMN IF NOT EXISTS adicional_bomba_gas  VARCHAR(12) NOT NULL DEFAULT '';
UPDATE clientes SET valor_ajudante_vuc = COALESCE(valor_ajudante, ''), adicional_mangueira = COALESCE(valor_adicional, '')
 WHERE valor_ajudante_vuc = '' AND adicional_mangueira = '';

ALTER TABLE pedidos ADD COLUMN IF NOT EXISTS valor_ajudante_vuc   VARCHAR(12) NOT NULL DEFAULT '';
ALTER TABLE pedidos ADD COLUMN IF NOT EXISTS valor_ajudante_toco  VARCHAR(12) NOT NULL DEFAULT '';
ALTER TABLE pedidos ADD COLUMN IF NOT EXISTS adicional_mangueira  VARCHAR(12) NOT NULL DEFAULT '';
ALTER TABLE pedidos ADD COLUMN IF NOT EXISTS adicional_bomba_gas  VARCHAR(12) NOT NULL DEFAULT '';
ALTER TABLE pedidos ADD COLUMN IF NOT EXISTS ajudante_toco        VARCHAR(10) NOT NULL DEFAULT 'NÃO';
ALTER TABLE pedidos ADD COLUMN IF NOT EXISTS bomba_gas            VARCHAR(10) NOT NULL DEFAULT 'NÃO';
UPDATE pedidos SET valor_ajudante_vuc = COALESCE(valor_ajudante, ''), adicional_mangueira = COALESCE(valor_adicional, '')
 WHERE valor_ajudante_vuc = '' AND adicional_mangueira = '';
