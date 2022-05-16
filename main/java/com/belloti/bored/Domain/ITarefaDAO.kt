package com.belloti.bored.Domain

import com.belloti.bored.Data.model.Tarefa


interface ITarefaDAO {
    fun salvar(tarefa: Tarefa?): Boolean
    fun atualizar(tarefa: Tarefa?): Boolean
    fun deletar(tarefa: Tarefa?): Boolean
    fun listar(): List<Tarefa?>?
}