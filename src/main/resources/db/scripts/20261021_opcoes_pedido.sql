BEGIN;

ALTER TABLE pedidos
    ADD COLUMN IF NOT EXISTS entrega_sabado VARCHAR(10) DEFAULT 'NÃO',
    ADD COLUMN IF NOT EXISTS exibir_preco VARCHAR(10) DEFAULT 'NÃO';

UPDATE pedidos SET entrega_sabado = 'NÃO' WHERE entrega_sabado IS NULL;
UPDATE pedidos SET exibir_preco = 'NÃO' WHERE exibir_preco IS NULL;

ALTER TABLE pedidos
    ALTER COLUMN entrega_sabado SET DEFAULT 'NÃO',
    ALTER COLUMN entrega_sabado SET NOT NULL,
    ALTER COLUMN exibir_preco SET DEFAULT 'NÃO',
    ALTER COLUMN exibir_preco SET NOT NULL;

COMMIT;
