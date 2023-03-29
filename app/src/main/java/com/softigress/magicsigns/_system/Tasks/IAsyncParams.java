package com.softigress.magicsigns._system.Tasks;

public interface IAsyncParams<input, output> {
    output run(input[] params);
    void onFinish(output result);
}